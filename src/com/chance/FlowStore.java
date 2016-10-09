package com.chance;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * 流程加载框架
 * Created by Chance on 16/09/23.
 */
public class FlowStore {
    public int TIMEOUT = 5;

    public String flowFile;
    public String flowStart;

    //所有静态流程节点存储
    public HashMap<String, FlowBean> flows = new HashMap<String, FlowBean>();
    //工作流全局变量存储
    public HashMap<String, String> vars = new HashMap<String, String>();
    //处理的流程时序（存储属性defaultstate或者subflow的流程）
    public Stack<String> subflow = new Stack<String>();
    //记录执行的流程历史
    public List<String> flowHistory = new ArrayList<String>();

    //从文件初始化工作流
    public FlowStore(String flowFile) {
        this.flowFile = flowFile;
        this.loadFlowFile(flowFile);
    }

    /**
     * 加载流程文件，执行defaultstate入口流程execWorkFlow(defaultstate)；
     * 如果流程存在defaultstate属性，表示为主流程，否则为子流程；
     * 主流程存在子流程元素，子流程存在输入输出；
     * 对于主流程，先将其压栈，然后执行他的defaultstate入口流程；
     * 对于子流程，判断其属性字段：
		如果子流程存在action属性，表示直接执行>action脚本；
		如果子流程存在subflow属性，表示需要跳转到其它主流程>subflow，此时需要将节点压栈；
     * 执行子流程时，如果targetstate为END，表示一条流程正常执行结束，此时将栈顶元素的result置为END表示完成并出栈；否则执行其对应result的出口；
     * 执行主流程时，如果此时result为END，判断是否满足工作流完成条件，
        如果此时栈为空并且入口节点result为END，表示所有流程正常完成；否则将栈顶元素的result置为END表示完成并出栈；
     */
    public void execWorkFlow(String start) throws Exception {
        FlowBean fb = flows.get(start);
        System.out.println("---------- ---------- ----------");
        System.out.println(">>> [Underway]：" + start);

        if (fb.get_DefaultState() != null) {//主流程
            subflow.push(fb.get_ID());
            flowHistory.add("in-" + fb.get_ID());
            System.out.println("> [Push]:" + fb.get_ID());
            execWorkFlow(fb.get_DefaultState());//>>>>> >>>>> >>>>>

            String result = fb.get_Result();
            if (result.equals("END")) {
                //工作流完成条件出口
                if (isWorkFlowAllCompleted()) {
                    return;
                }
                FlowBean pop = flows.get(subflow.pop());
                flowHistory.add("out-" + pop.get_ID());
                System.out.println("> [Pop]:" + pop.get_ID());
                pop.set_Result("END");
            } else {
                throw new Exception("Child Flow(" + fb.get_ID() + ") Runtime Error!");
            }
        } else {//子流程
            if (fb.get_Subflow() != null) {
                subflow.push(fb.get_ID());
                flowHistory.add("in-" + fb.get_ID());
                System.out.println("> [Push]:" + fb.get_ID());
                execWorkFlow(fb.get_Subflow());//>>>>> >>>>> >>>>>

                String result = flows.get(fb.get_Subflow()).get_Result();
                if (result.equals("END")) {
                    if (fb.get_Next().get("ok").equals("END")) {
                        FlowBean pop = flows.get(subflow.pop());
                        flowHistory.add("out-" + pop.get_ID());
                        System.out.println("> [Pop]:" + pop.get_ID());
                        pop.set_Result("END");//??????
                    } else if (flows.containsKey(fb.get_Next().get("ok"))) {
                        execWorkFlow(fb.get_Next().get("ok"));//>>>>> >>>>> >>>>>
                    } else {
                        throw new Exception("Child subflow(" + fb.get_Subflow() + ") miss result [ok]!");
                    }
                } else {
                    throw new Exception("Child Flow(" + fb.get_Subflow() + ") Runtime Error!");
                }
            } else if (fb.get_Action() != null) {
                flowHistory.add("do-" + fb.get_ID());
                loadInstant_In(fb);//装载变量引用
                fb.set_Result(null);

                //同步方法
//                String result = (String) fb.runAction();
//                fb.set_Result(result);

                //设置状态的保持和超时
                fb.runAction(new JSEngine());//异步回调，设置result

                int pre = new Date().getSeconds();
                while (fb.get_Result() == null) {
                    int now = new Date().getSeconds();
                    if (now - pre > getTIMEOUT()) {
                        throw new Exception("[" + fb.get_Action() + "]状态超时！");
                    }
                }
                String result = fb.get_Result();

                System.out.println("Action(" + fb.get_Action() + ") returns " + result);
//                //>>>重置out
                if (fb.get_Next().containsKey(result)) {
                    if (fb.get_Next().get(result).equals("END")) {
                        FlowBean pop = flows.get(subflow.pop());
                        flowHistory.add("out-" + pop.get_ID());
                        System.out.println("> [Pop]:" + pop.get_ID());
                        pop.set_Result("END");
                    } else if (flows.containsKey(fb.get_Next().get(result))) {
                        execWorkFlow(fb.get_Next().get(result));//>>>>> >>>>> >>>>>
                    }
                } else {
                    throw new Exception("Child Flow(" + fb.get_ID() + ") miss the result of " + result);
                }
            } else {
                throw new Exception("The Workflow contains illegal attributes!");
            }
        }
    }

    //加载解析工作流文件
    public void loadFlowFile(String flowFile) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(Config.URL_RES + flowFile));
            Element root = document.getRootElement();
            System.out.println("<WorkFlow>：" + flowFile +
                    " - defaultstate：" + root.attribute("defaultstate").getValue());
            setFlowStart(root.attribute("defaultstate").getValue());

            //全局变量
            System.out.println("---------- ---------- ----------");
            List tempdatas = root.element("tempdatachart").elements("tempdata");
            for (Iterator i = tempdatas.iterator(); i.hasNext(); ) {
                Element e = (Element) i.next();
                vars.put(e.attribute("id").getValue(), e.getText());
                System.out.println("+ " + e.attribute("id").getValue()
                        + " -" + e.attribute("comment").getValue() + e.getText());
            }

            //主流程
            List mainFlows = root.elements("state");
            for (Iterator i = mainFlows.iterator(); i.hasNext(); ) {
                Element e = (Element) i.next();
                System.out.println("---------- ---------- ----------");
                System.out.println(">>>>>> [M]" + e.attribute("id").getValue());
                System.out.println("# defaultstate：" + e.attribute("defaultstate").getValue());

                FlowBean fb = new FlowBean();
                fb.set_ID(e.attribute("id").getValue());
                fb.set_DefaultState(e.attribute("defaultstate").getValue());
                flows.put(fb.get_ID(), fb);

                //子流程
                List childFlows = e.elements("state");
                for (Iterator i2 = childFlows.iterator(); i2.hasNext(); ) {
                    Element e2 = (Element) i2.next();
                    System.out.println(">>> [C]" + e2.attribute("id").getValue());

                    FlowBean fb2 = new FlowBean();
                    fb2.set_ID(e2.attribute("id").getValue());
                    if (e2.attribute("action") != null) {
                        fb2.set_Action(e2.attribute("action").getValue());
                        System.out.println("@ action：" + e2.attribute("action").getValue());
                    } else if (e2.attribute("subflow") != null) {
                        fb2.set_Subflow(e2.attribute("subflow").getValue());
                        System.out.println("@ subflow：" + e2.attribute("subflow").getValue());
                    }

                    List trans = e2.elements("transition");
                    for (Iterator it = trans.iterator(); it.hasNext(); ) {
                        Element em = (Element) it.next();
                        fb2.get_Next().put(em.attribute("result").getValue(), em.attribute("targetstate").getValue());
                        System.out.println("& " + em.attribute("result").getValue()
                                + " - " + em.attribute("targetstate").getValue());
                    }

                    if (e2.element("inparamchart") != null && e2.element("inparamchart").elements("inparam") != null) {
                        List ins = e2.element("inparamchart").elements("inparam");
                        for (Iterator it = ins.iterator(); it.hasNext(); ) {
                            Element em = (Element) it.next();
                            fb2.get_In().add(em.attribute("id").getValue());
                            System.out.println("- in：" + em.attribute("id").getValue());
                        }
                    }
                    if (e2.element("outparamchart") != null && e2.element("outparamchart").elements("outparam") != null) {
                        List outs = e2.element("outparamchart").elements("outparam");
                        for (Iterator it = outs.iterator(); it.hasNext(); ) {
                            Element em = (Element) it.next();
                            fb2.get_Out().add(em.attribute("id").getValue());
                            System.out.println("- out：" + em.attribute("id").getValue());
                        }
                    }
                    flows.put(fb2.get_ID(), fb2);
                }
            }
            System.out.println(flows.keySet().toString());
            System.out.println("</WorkFLow>：" + flowFile + " - END");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对fb的变量进行加工，动态装载变量引用:
     * 比如“等待插卡”状态包含一个id为“$Account”输入，
     * 表示其持有对全局变量Account的引用
     */
    public void loadInstant_In(FlowBean fb) {
        for (int i = 0; i < fb.get_In().size(); i++) {
            String in = fb.get_In().get(i);
            if (in.startsWith("$")) {
                if (getInstantVars().containsKey(in.substring(1))) {
                    String val = getInstantVars().get(in.substring(1));
                    fb.get_In().set(i, val);
                }
            }
        }
    }

    //模拟拿到实际机器参数
    public HashMap<String, String> getInstantVars() {
        if (vars.containsKey("Account")) {
            vars.put("Account", "Account1234");
        }
        if (vars.containsKey("Account")) {
            vars.put("Amount", "Amount1234");
        }
        if (vars.containsKey("TransType")) {
//            int i = new Random().nextInt(10);
//            if (i >= 0 && i < 5) {
//                vars.put("TransType", "draw");
//            } else if (i >= 5 && i < 7) {
//                vars.put("TransType", "query");
//            } else if (i >= 7 && i < 9) {
//                vars.put("TransType", "trans");
//            } else {
//                vars.put("TransType", "exit");
//            }
//            Boolean b = new Random().nextBoolean();
//            if(b){
//                vars.put("TransType", "draw");
//            }else {
//            vars.put("TransType", "exit");
//            }
        }
        if (vars.containsKey("TransInfo")) {
            vars.put("TransInfo", "TransInfo1234");
        }
        return vars;
    }

    public Boolean isWorkFlowAllCompleted() {
        if (subflow.size() == 0 && flows.get(flowStart).get_Result().equals("END")) {
            System.out.println("The WorkFlow has been finished successfully!");
            System.out.println(flowHistory.toString());
            return true;
        }
        return false;
    }

    public String getFlowStart() {
        return flowStart;
    }

    public void setFlowStart(String flowStart) {
        this.flowStart = flowStart;
    }

    public int getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(int TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    public static void main(String[] args) throws Exception {
        FlowStore fs = new FlowStore("CusApp2.xml");
        fs.setTIMEOUT(15);
        fs.execWorkFlow(fs.getFlowStart());
    }
}
