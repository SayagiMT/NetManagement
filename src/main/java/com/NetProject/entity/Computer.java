package com.NetProject.entity;

public class Computer {
    private int id;
    private String name;
    private String status;
    private double prices;
    public Computer(){}
    public Computer(int id, String name, String status, double prices) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.prices = prices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }
}
