package com.chance;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Chance on 16/10/08.
 */
public class SocketHelper {

    public static String IP = "localhost";
    public static int PORT = 10010;

    public static void sendTcp(String msg) {
        try {
            Socket s = new Socket(IP, PORT);
            OutputStream out = s.getOutputStream();
            out.write(msg.getBytes());
            InputStream in = s.getInputStream();
            byte[] buf = new byte[1024];
            int len = in.read(buf);
            System.out.println(new String(buf, 0, len));
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createSocketServer() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            Socket s = ss.accept();
            InputStream in = s.getInputStream();
            byte[] buf = new byte[1024];
            int len = in.read(buf);
            String msg = new String(buf, 0, len);
            System.out.println(new Date().toLocaleString() + " -> " + msg);

            OutputStream out = s.getOutputStream();
            String result = "UpperCase Msg ："+msg.toUpperCase();
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
                System.out.println("listen to port "+ PORT);
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
