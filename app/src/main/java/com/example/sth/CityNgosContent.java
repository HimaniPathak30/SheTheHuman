package com.example.sth;

public class CityNgosContent {

    String ngo,add,content,mob,email;
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public CityNgosContent(String ngo, String add, String content, String mob, String email) {
        this.ngo = ngo;
        this.add = add;
        this.content = content;
        this.mob = mob;
        this.email = email;
        this.expandable = false;
    }

    public String getNgo() {
        return ngo;
    }

    public String getAdd() {
        return add;
    }

    public String getContent() {
        return content;
    }

    public String getMob() {
        return mob;
    }

    public String getEmail() {
        return email;
    }

    public void setNgo(String ngo) {
        this.ngo = ngo;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ArunachalContent{" +
                "ngo='" + ngo + '\'' +
                ", add='" + add + '\'' +
                ", content='" + content + '\'' +
                ", mob='" + mob + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
