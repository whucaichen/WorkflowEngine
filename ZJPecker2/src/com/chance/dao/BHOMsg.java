package com.chance.dao;

/**
 * Created by Chance on 16/10/17.
 */
public class BHOMsg {

    public String loginid;
    public String processid;
    public String destinationid;
    public Datagram datagram;

    public Datagram getDatagram() {
        return datagram;
    }

    public void setDatagram(Datagram datagram) {
        this.datagram = datagram;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public String getDestinationid() {
        return destinationid;
    }

    public void setDestinationid(String destinationid) {
        this.destinationid = destinationid;
    }
}
