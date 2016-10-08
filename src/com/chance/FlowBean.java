package com.chance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 流程节点描述类
 * action字段确定节点脚本名还是子流程名
 * 如果名称带".xml"后缀，说明是子流程；否则为普通JS脚本
 * Created by Chance on 16/09/22.
 */
public class FlowBean implements ICallBack {

    public String _ID;
    public String _Name;
    public String _Action;
    public String _Subflow;
    public String _DefaultState;
    public String _Result;//脚本返回

    public String _Pre;
    public HashMap<String, String> _Next = new HashMap<String, String>();
    List<String> _In = new ArrayList<String>();
    List<String> _Out = new ArrayList<String>();
    //节点状态 进行中（underway），等待处理（queue），完成（finish）

    //同步执行脚本方法
    public Object runAction() {
        return new JSEngine().invoke(get_Action(), get_In().toArray());
    }

    //异步执行脚本方法
    public void runAction(final JSEngine jsEngine) {
        new Thread() {
            @Override
            public void run() {
                jsEngine.invoke(FlowBean.this, get_Action(), get_In().toArray());
            }
        }.start();

        System.out.println("The JS is going on, now waiting the result……");
    }

    //回调函数
    @Override
    public void complete(Object result) {
        System.out.println("……Receive the result：" + result);
        set_Result((String) result);
    }

    public String get_Action() {
        return _Action;
    }

    public void set_Action(String _Action) {
        this._Action = _Action;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public HashMap<String, String> get_Next() {
        return _Next;
    }

    public void set_Next(HashMap<String, String> _Next) {
        this._Next = _Next;
    }

    public String get_Pre() {
        return _Pre;
    }

    public void set_Pre(String _Pre) {
        this._Pre = _Pre;
    }

    public String get_Result() {
        return _Result;
    }

    public void set_Result(String _Result) {
        this._Result = _Result;
    }

    public String get_Subflow() {
        return _Subflow;
    }

    public void set_Subflow(String _Subflow) {
        this._Subflow = _Subflow;
    }

    public String get_DefaultState() {
        return _DefaultState;
    }

    public void set_DefaultState(String _DefaultState) {
        this._DefaultState = _DefaultState;
    }

    public List<String> get_In() {
        return _In;
    }

    public void set_In(List<String> _In) {
        this._In = _In;
    }

    public List<String> get_Out() {
        return _Out;
    }

    public void set_Out(List<String> _Out) {
        this._Out = _Out;
    }
}
