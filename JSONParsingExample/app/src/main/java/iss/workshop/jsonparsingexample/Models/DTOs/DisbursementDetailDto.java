package iss.workshop.jsonparsingexample.Models.DTOs;

public class DisbursementDetailDto {

    private int Id;
    private String ItemCode;
    private String ItemName;
    private int Qty;

    public DisbursementDetailDto() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
