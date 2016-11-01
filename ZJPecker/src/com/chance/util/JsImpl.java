package com.chance.util;

/**
 * Created by Chance on 16/10/31.
 */
public abstract class JsImpl {

    public void doSth(){
        System.out.println("doSth");
        System.out.println(doAny());
    }

    public abstract String doAny();
}
