package com.example.transportation;

public class tyremodel {

    String Name, Vehicle, Tyre, Ammount, Balance;

    tyremodel(){

    }

    public tyremodel(String name, String vehicle, String tyre, String ammount, String balance) {
        Name = name;
        Vehicle = vehicle;
        Tyre = tyre;
        Ammount = ammount;
        Balance = balance;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public String getTyre() {
        return Tyre;
    }

    public void setTyre(String tyre) {
        Tyre = tyre;
    }

    public String getAmmount() {
        return Ammount;
    }

    public void setAmmount(String ammount) {
        Ammount = ammount;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
