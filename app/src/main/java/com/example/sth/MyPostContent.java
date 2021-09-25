package com.example.sth;

public class MyPostContent {
    String username, email, uid, pid, ptime, pmsg, pComments;

    public MyPostContent() {
    }

    public MyPostContent(String username, String email, String uid, String pid, String ptime, String pmsg, String pComments) {
        this.username = username;
        this.email = email;
        this.uid = uid;
        this.pid = pid;
        this.ptime = ptime;
        this.pmsg = pmsg;
        this.pComments = pComments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getPmsg() {
        return pmsg;
    }

    public void setPmsg(String pmsg) {
        this.pmsg = pmsg;
    }

    public String getpComments() {
        return pComments;
    }

    public void setpComments(String pComments) {
        this.pComments = pComments;
    }
}