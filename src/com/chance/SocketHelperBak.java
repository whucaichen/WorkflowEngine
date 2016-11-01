package com.chance;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Chance on 16/10/08.
 */
public class SocketHelperBak {

    public static String IP = "localhost";
    //    public static String IP = "10.34.10.122";
    public static int PORT = 50026;

    //TCP Client
    public static String sendTcp(String msg) {
        String result = null;
        try {
            Socket s = new Socket(IP, PORT);
            OutputStream out = s.getOutputStream();
            out.write(msg.getBytes());
            InputStream in = s.getInputStream();
            byte[] buf = new byte[1024];
            int len = in.read(buf);
            result = new String(buf, 0, len);
            System.out.println(result);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //包含报文头字段
    public static Boolean sendZJTcp(String msg) {
        try {
            Socket s = new Socket(IP, PORT);
            OutputStream out = s.getOutputStream();

            byte[] srcData = msg.getBytes();
            int length = msg.length();
            byte[] lenData = to4Bytes(length);
            byte[] dstData = new byte[length + 4];//报文结构：4字节报文长度＋报文正文
            System.arraycopy(lenData, 0, dstData, 0, 4);
            System.arraycopy(srcData, 0, dstData, 4, srcData.length);

            printBytes(dstData);
            out.write(dstData);
            out.flush();
            s.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //TCP Server
    public static void createSocketServer() {
        String result = null;
        try {
            ServerSocket ss = new ServerSocket(PORT);
            Socket s = ss.accept();
            InputStream in = s.getInputStream();
            byte[] buf = new byte[1024];
            int len = in.read(buf);
            String msg = new String(buf, 0, len);
            System.out.println(new Date().toLocaleString() + " -> " + msg);

            OutputStream out = s.getOutputStream();
            if (msg.equals("Menu Select")) {
                Scanner input = new Scanner(System.in);
                System.out.print("请输入交易类型(draw、query、trans、exit)：");
                result = input.next();
            } else {
                result = "UpperCase Msg ：" + msg.toUpperCase();
            }
            out.write(result.getBytes());
            s.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        TestClient();
        TestServer();
    }

    public static void TestServer() {
        //模拟服务端
        new Thread() {
            @Override
            public void run() {
                System.out.println("listen to port " + PORT);
                while (true) {
                    createSocketServer();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static void TestClient() {
        //真实的客户端
        Gson gson = new Gson();
        Msg data = new Msg();
        data.setLoginid("finesse");
        data.setProcessid("login");
//        data.setData("");
        String jsonData = gson.toJson(data);
        System.out.println(jsonData);
        Object result = sendZJTcp(jsonData);
        System.out.println(result);
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
}
