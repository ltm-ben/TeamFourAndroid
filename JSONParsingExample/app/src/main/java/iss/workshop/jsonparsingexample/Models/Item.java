package iss.workshop.jsonparsingexample.Models;

public class Item {

    private String name;
    private int unitPrice;
    private int prediction;
    private int qty;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getPrediction() {
        return prediction;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }
}
