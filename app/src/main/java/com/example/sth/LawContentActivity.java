package com.example.sth;

public class LawContentActivity {

    private String lawName, lawDetails;
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public LawContentActivity(String lawName, String lawDetails) {
        this.lawName = lawName;
        this.lawDetails = lawDetails;
        this.expandable = false;

    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {

        this.lawName = lawName;
    }

    public String getLawDetails() {
        return lawDetails;
    }

    public void setLawDetails(String lawDetails) {
        this.lawDetails = lawDetails;
    }

    @Override
    public String toString() {
        return "LawContentActivity{" +
                "lawName='" + lawName + '\'' +
                ", lawDetails='" + lawDetails + '\'' +
                '}';
    }
}
