package com.sdm.NPP.model;

public class Address {
    private String number;
    private String line1;
    private String line2;
    private String division;

    public Address() {}

    public Address(String number, String line1, String line2, String division) {
        this.number = number;
        this.line1 = line1;
        this.line2 = line2;
        this.division = division;
    }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getLine1() { return line1; }
    public void setLine1(String line1) { this.line1 = line1; }

    public String getLine2() { return line2; }
    public void setLine2(String line2) { this.line2 = line2; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
}
