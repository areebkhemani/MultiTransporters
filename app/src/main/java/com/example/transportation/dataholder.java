package com.example.transportation;

public class dataholder {

    String name,cnic,vehicle,company, pimage;

    public dataholder(String name, String cnic, String vehicle, String company, String pimage) {
        this.name = name;
        this.cnic = cnic;
        this.vehicle = vehicle;
        this.company = company;
        this.pimage = pimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
