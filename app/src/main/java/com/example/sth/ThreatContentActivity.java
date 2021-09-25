package com.example.sth;

public class ThreatContentActivity {

    private String threatQue,threatAns;
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public ThreatContentActivity(String threatQue, String threatAns) {
        this.threatQue = threatQue;
        this.threatAns = threatAns;
        this.expandable = false;
    }

    public String getThreatQue() {
        return threatQue;
    }

    public void setThreatQue(String threatQue) {
        this.threatQue = threatQue;
    }

    public String getThreatAns() {
        return threatAns;
    }

    public void setThreatAns(String threatAns) {
        this.threatAns = threatAns;
    }

    @Override
    public String toString() {
        return "ThreatContentActivity{" +
                "threatQue='" + threatQue + '\'' +
                ", threatAns='" + threatAns + '\'' +
                '}';
    }
}
