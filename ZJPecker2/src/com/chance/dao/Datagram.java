package com.chance.dao;

/**
 * Created by Chance on 16/10/18.
 */
public class Datagram {
    /**
     * cmdcode : 010001
     * requestid : 1000010101010101010
     * trantime : 2016-08-30 14:36:20
     */

    private HeadBean head = new HeadBean();
    /**
     * type : 1
     * action : click
     * buttonname : 查询
     */

    private BodyBean body = new BodyBean();

    public HeadBean getHead() {
        return head;
    }

    public void setHead(HeadBean head) {
        this.head = head;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class HeadBean {
        private String cmdcode;
        private String requestid;
        private String trantime;

        public String getCmdcode() {
            return cmdcode;
        }

        public void setCmdcode(String cmdcode) {
            this.cmdcode = cmdcode;
        }

        public String getRequestid() {
            return requestid;
        }

        public void setRequestid(String requestid) {
            this.requestid = requestid;
        }

        public String getTrantime() {
            return trantime;
        }

        public void setTrantime(String trantime) {
            this.trantime = trantime;
        }
    }

    public static class BodyBean {
        private String type;
        private String action;
        private String buttonname;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getButtonname() {
            return buttonname;
        }

        public void setButtonname(String buttonname) {
            this.buttonname = buttonname;
        }
    }
}
