package iss.workshop.jsonparsingexample.Models.DTOs;

public class DisbursementDetailDto {

    private int Id;
    private StationeryDto Stationery;
    private int Qty;

    public DisbursementDetailDto() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public StationeryDto getStationery() {
        return Stationery;
    }

    public void setStationery(StationeryDto stationery) {
        Stationery = stationery;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
