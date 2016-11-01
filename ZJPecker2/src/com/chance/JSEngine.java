package com.chance;

import com.chance.util.Attr;
import com.chance.util.ICallBack;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JS脚本执行类
 * Created by Chance on 16/09/22.
 */
public class JSEngine {

    String jsFileName = Attr.URL_JS;   // 读取js文件
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("javascript");
//    ScriptEngine engine = manager.getEngineByName("nashorn");

    //异步回调
    public void invoke(ICallBack iCallBack, String funcName, Object... parmas) {
        Object result = null;
        try {
            FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                result = invoke.invokeFunction(funcName, parmas);
            }
            reader.close();

        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        iCallBack.complete(result);
    }

    //同步方法
    public Object invoke(String funcName, Object... parmas) {
        Object result = null;
        try {
            FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                result = invoke.invokeFunction(funcName, parmas);
            }
            reader.close();

        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    //装载JS全局变量（非函数参数）
    public void setJSArgs(HashMap jsArgs) {
        Iterator i = jsArgs.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            engine.put((String) key, val);
        }
    }

    public static String hello() {
        System.out.println("hello");
        return "hello";
    }

    //测试wsap.js对com.chance.JSEngine.hello()的调用
    public String jsCallJava() {
        this.invoke("callJava");
        List list2 = (List) engine.get("list2");
        return list2.toString();
    }

    //测试wsap.js利用com.chance.SocketHelper.sendTcp(msg)来发送Socket报文
    public void sendTcp(String msg) {
        this.invoke("sendTcp", msg);
    }

    public static void main(String[] args) throws Exception {
        JSEngine je = new JSEngine();
        je.invoke("entryAction");
    }
}
