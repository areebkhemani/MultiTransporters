package com.example.transportation;

public class paymentdataholder {

    String name, advance, fuel, tyre, months, lastedit;

    public paymentdataholder(String name, String advance, String fuel, String tyre, String months, String lastedit) {
        this.name = name;
        this.advance = advance;
        this.fuel = fuel;
        this.tyre = tyre;
        this.months = months;
        this.lastedit = lastedit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTyre() {
        return tyre;
    }

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }
    public String getLastedit() {
        return lastedit;
    }

    public void setLastedit(String lastedit) {
        this.lastedit = lastedit;
    }
}
