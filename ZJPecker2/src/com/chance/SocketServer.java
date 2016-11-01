package com.chance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Chance on 16/10/19.
 */
public class SocketServer {

    public static String IP = "localhost";
    //    public static String IP = "10.34.10.122";
    public static int PORT = 50026;

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("listen to port " + PORT);

        while (true) {
            Socket s = ss.accept();
            final MyServer myServer = new MyServer(s) {
                @Override
                public void onReceive(InetAddress inetAddress, String s) {
                    System.out.println(new Date().toLocaleString() + " -> " + s);
                }
            };
            new Thread(myServer).start();

            new Thread() {
                @Override
                public void run() {
                    Scanner input = new Scanner(System.in);
                    String msg = input.nextLine();
                    myServer.send(msg);
                }
            }.start();
        }
//        ss.close();
    }

    static abstract class MyServer implements Runnable {
        public Socket mSocket;
        public InputStream in;
        public OutputStream out;

        public MyServer(Socket mSocket) {
            this.mSocket = mSocket;
        }

        @Override
        public void run() {
            try {
                in = this.mSocket.getInputStream();
                out = this.mSocket.getOutputStream();
                while (true) {
                    byte[] buf = new byte[1024];
                    int len;
                    String msg;
                    while ((len = in.read(buf)) != -1) {
                        msg = new String(buf, 0, len);
                        this.onReceive(mSocket.getInetAddress(), msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean send(String jsonMsg) {
            try {
                if (mSocket.isOutputShutdown()) {
                    out = this.mSocket.getOutputStream();
                }
                if (out != null) {
                    if (jsonMsg.equals("exit")) {
                        in.close();
                        out.close();
                        mSocket.close();
                    }
                    DecimalFormat df = new DecimalFormat("0000");//报文结构：4字节报文长度＋报文正文
                    String msgLen = df.format(jsonMsg.length());
                    String msgPacket = msgLen + jsonMsg;
                    System.out.println(msgPacket);
                    out.write(msgPacket.getBytes());
                    out.flush();
                    return true;
                }
//                mSocket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        public abstract void onReceive(InetAddress inetAddress, String s);
    }
}
