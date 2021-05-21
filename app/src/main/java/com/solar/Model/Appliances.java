package com.solar.Model;

public class Appliances {


    private String mApplianceName;
    private String mApplianceWattage;
    private String mApplianceQuantity;
    private String bettryType;
    private String InverterType;
    private String PlatesType;

    public Appliances() {
    }

    public Appliances(String mApplianceName, String mApplianceWattage, String mApplianceQuantity, String bettryType, String inverterType, String platesType) {
        this.mApplianceName = mApplianceName;
        this.mApplianceWattage = mApplianceWattage;
        this.mApplianceQuantity = mApplianceQuantity;
        this.bettryType = bettryType;
        InverterType = inverterType;
        PlatesType = platesType;
    }


    public String getmApplianceName() {
        return mApplianceName;
    }

    public void setmApplianceName(String mApplianceName) {
        this.mApplianceName = mApplianceName;
    }

    public String getmApplianceWattage() {
        return mApplianceWattage;
    }

    public void setmApplianceWattage(String mApplianceWattage) {
        this.mApplianceWattage = mApplianceWattage;
    }

    public String getmApplianceQuantity() {
        return mApplianceQuantity;
    }

    public void setmApplianceQuantity(String mApplianceQuantity) {
        this.mApplianceQuantity = mApplianceQuantity;
    }

    public String getBettryType() {
        return bettryType;
    }

    public void setBettryType(String bettryType) {
        this.bettryType = bettryType;
    }

    public String getInverterType() {
        return InverterType;
    }

    public void setInverterType(String inverterType) {
        InverterType = inverterType;
    }

    public String getPlatesType() {
        return PlatesType;
    }

    public void setPlatesType(String platesType) {
        PlatesType = platesType;
    }
}
