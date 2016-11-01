package com.chance.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Chance on 16/10/31.
 */
public abstract class JsImpl {

    public void doSth(){
        System.out.println("doSth");
        System.out.println(doAny());
    }

    public abstract String doAny();

    public static void main(String[] args) {
        Object result = null;
        String jsFileName = "js/Nashorn.js";   // 读取js文件
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                result = invoke.invokeFunction("start", null);
            }
            reader.close();
            System.out.println(result);

        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
