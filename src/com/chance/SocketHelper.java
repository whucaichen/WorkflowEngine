package com.chance;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Chance on 16/10/08.
 */
public class SocketHelper {

    public static String IP = "localhost";
    public static int PORT = 10010;

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
}
