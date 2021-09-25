package com.example.sth;

public class AddContactData {

    String name, number, uid, con_id;

    public AddContactData() {
    }

    public AddContactData(String name, String number, String uid, String con_id) {
        this.name = name;
        this.number = number;
        this.uid = uid;
        this.con_id = con_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCon_id() {
        return con_id;
    }

    public void setCon_id(String con_id) {
        this.con_id = con_id;
    }
}