package com.agh.expenseapp;

public class SummaryListData {

    private String value;
    private String purpose;
    private int imgId;

    public SummaryListData(String value, String purpose, int imgId) {
        this.value = value;
        this.purpose = purpose;
        this.imgId = imgId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String desc2) {
        this.purpose = desc2;
    }
    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

}
