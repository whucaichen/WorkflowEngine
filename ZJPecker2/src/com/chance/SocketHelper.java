package com.chance;

/**
 * Socket收发器 通过Socket发送数据，并使用新线程监听Socket接收到的数据
 */

import com.chance.dao.BHOMsg;
import com.chance.dao.Datagram;
import com.chance.pwd.ByteUtil;
import com.chance.pwd.MacUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class SocketHelper implements Runnable {

            final static String IP = "localhost";
//    final static String IP = "10.34.10.122";
    final static int PORT = 50026;

    public Socket mSocket;
    public InetAddress mInetAddress;
    public InputStream in;
    public OutputStream out;
    public boolean isRun;
    public boolean isLogin;

    public SocketHelper(Socket socket) {
        this.mSocket = socket;
        this.mInetAddress = socket.getInetAddress();
    }

    public InetAddress getInetAddress() {
        return mInetAddress;
    }

    public boolean isLogin() {
        return this.isLogin;
    }

    /**
     * 开启Socket收发
     * 如果开启失败，会断开连接并回调onDisconnect()
     */
    public void start() {
        isRun = true;
        new Thread(this).start();
    }

    /**
     * 封装CommServer的json数据
     *
     * @param loginID
     * @param processid
     * @param destinationId
     * @param datagram
     * @return
     */
    public boolean sendMsg(String loginID, String processid, String destinationId, Datagram datagram) {
        BHOMsg BHOMsg = new BHOMsg();
        BHOMsg.setLoginid(loginID);
        BHOMsg.setProcessid(processid);
        BHOMsg.setDestinationid(destinationId);
        BHOMsg.setDatagram(datagram);
        String jsonMsg = new Gson().toJson(BHOMsg);
        return send(jsonMsg);
    }

    /**
     * 发送TCP报文
     *
     * @param jsonMsg
     * @return
     */
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
                DecimalFormat df = new DecimalFormat("0000");//报文结构：4字节报文长度＋报文正文+校验码
                String msgLen = df.format(jsonMsg.length());
                //加入校验信息,mac919的data参数？
                String msgCheck = MacUtil.Mac_919(MacUtil.getUUID(), null,
                        ByteUtil.getHexStr(jsonMsg.getBytes()));//发送数据的原始16进制
                String msgPacket = msgLen + jsonMsg + msgCheck;
                System.out.println(msgPacket);
                out.write(msgPacket.getBytes());
                //                out.write(msgPacket.getBytes("utf-8"));
                out.flush();
                return true;
            }
            mSocket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 监听Socket接收的数据(新线程中运行)
     */
    @Override
    public void run() {
        try {
            in = this.mSocket.getInputStream();
            out = this.mSocket.getOutputStream();
            isLogin = sendMsg("zjpecker", "login", null, null);//登录

            while (isRun) {
                byte[] buf = new byte[1024];
                int len;
                String msg;
                while ((len = in.read(buf)) != -1) {
                    msg = new String(buf, 0, len);
                    this.onReceive(getInetAddress(), msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            isRun = false;
        }
        // 断开连接
        try {
            in.close();
            out.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.onDisconnect(getInetAddress());
    }

    /**
     * 断开连接(主动)
     * 连接断开后，会回调onDisconnect()
     */
    public void stop() {
        isRun = false;
        try {
            mSocket.shutdownInput();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收到数据
     * 注意：此回调是在新线程中执行的
     *
     * @param inetAddress 连接到的Socket地址
     * @param s           收到的字符串
     */
    public abstract void onReceive(InetAddress inetAddress, String s);

    /**
     * 连接断开
     * 注意：此回调是在新线程中执行的
     *
     * @param inetAddress 连接到的Socket地址
     */
    public abstract void onDisconnect(InetAddress inetAddress);

    public static void main(String[] args) throws IOException {
        Socket s = new Socket(IP, PORT);
        final SocketHelper sh = new SocketHelper(s) {
            @Override
            public void onReceive(InetAddress inetAddress, String s) {
                System.out.println(">>> Received message from " + inetAddress.getHostName());
                System.out.println(new Date().getTime() + " -> " + s);
            }

            @Override
            public void onDisconnect(InetAddress inetAddress) {
                System.out.println(">>> Socket disconnected with " + inetAddress.getHostName());
            }
        };
        sh.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Scanner input = new Scanner(System.in);
                String str = input.nextLine();
//                sh.send(str);

                Datagram datagram = new Datagram();
                datagram.getHead().setCmdcode("010001");
                datagram.getHead().setRequestid("1000010101010101010");
                datagram.getHead().setTrantime("2016-08-30 14:36:20");
                datagram.getBody().setType("1");
                datagram.getBody().setAction("click");
                datagram.getBody().setButtonname(str);
                sh.sendMsg("zjpecker", "senddata", "bho", datagram);
            }
        }.start();
//
//        sh.stop();
    }
}

