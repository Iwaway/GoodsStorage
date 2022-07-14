package com.ukma.yehor.cs_goodsstorage.model.StorageTools;

public class Good {
    private String name;
    private String code;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Good(String name, String code, double price){
        this.name = name;
        this.code = code;
        this.price = price;
    }

}
