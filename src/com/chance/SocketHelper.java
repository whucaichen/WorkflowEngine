package com.chance;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;

/**
 * Created by Chance on 16/10/08.
 */
public class SocketHelper implements Runnable{

    public static String IP = "10.34.10.122";
    public static int PORT = 50026;
    public static Socket mSocket;

    public SocketHelper() {
        try {
            mSocket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //包含报文头字段
    public boolean sendZJTcp(String msg) {
        try {
            if (mSocket == null)
                mSocket = new Socket(IP, PORT);

            OutputStream out = mSocket.getOutputStream();
            int length = msg.length();
            DecimalFormat df = new DecimalFormat("0000");//报文结构：4字节报文长度＋报文正文
            String lenStr = df.format(length);
            String dstData = lenStr + msg;
            System.out.println(dstData);
            out.write(dstData.getBytes("utf-8"));
            out.flush();
            System.out.println(mSocket.isClosed());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean closeSocket() {
        try {
            mSocket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendMsg(String loginID, String processid, String destinationId, Data data) {
        //真实的客户端
        Msg msg = new Msg();
        msg.setLoginid(loginID);
        msg.setProcessid(processid);
        msg.setDestinationid(destinationId);
        msg.setData(data);
        String jsonMsg = new Gson().toJson(msg);
        sendZJTcp(jsonMsg);
    }

    public static byte[] to4Bytes(int src) {
        DecimalFormat df = new DecimalFormat("0000");
        String s = df.format(src);
        byte[] dest = s.getBytes();
        System.out.println(s);
        printBytes(dest);
        return dest;
    }

    public static void printBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++)
            System.out.print(bytes[i] + " ");
        System.out.println("");
    }

    @Override
    public void run() {
        sendMsg("zjpecker", "login", null, null);
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Test() {
        SocketHelper sh = new SocketHelper();
        sh.sendMsg("zjpecker", "login", null, null);

        Data data = new Data();
        data.getHead().setCmdcode("010001");
        data.getHead().setRequestid("1000010101010101010");
        data.getHead().setTrantime("2016-08-30 14:36:20");
        data.getBody().setType("1");
        data.getBody().setAction("click");
        data.getBody().setButtonname("test");

        sh.sendMsg("zjpecker", "senddata", "bho", data);

//        sh.closeSocket();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        SocketHelper sh = new SocketHelper();
        sh.run();
    }
}
