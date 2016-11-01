package com.chance.util;

import com.chance.App;

import javax.swing.*;
import java.net.URL;

/**
 * 属性常量表
 * Created by Chance on 16/09/22.
 */
public class Attr {

    public static ImageIcon getImgRes(String img){
        URL url = App.class.getResource("res/"+ img);
        return new ImageIcon(url);
    }

    //资源路径
    public final static String URL_JS = "js/wsap.js";
//    public final static String URL_JS = "js/test.js";
    public final static String URL_RES = "res/";

    //流程文件字段
    public final static String FLOW_ID = "id";
    public final static String FLOW_NAME = "name";
    public final static String FLOW_ACTION = "action";
    public final static String FLOW_END = "END";
}
