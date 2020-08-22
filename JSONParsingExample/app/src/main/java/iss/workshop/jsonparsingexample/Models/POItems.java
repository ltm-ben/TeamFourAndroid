package iss.workshop.jsonparsingexample.Models;

import java.util.ArrayList;
import java.util.List;

public class POItems {

    public String orderDate;

    public PurchaseOrderStatus poStatus;

    public int supplierID;

    public List<PODetails> poDetailsList = new ArrayList<>();

    public void setPoDetailsList(List<PODetails> poDetailsList) {
        this.poDetailsList = poDetailsList;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setPoStatus(PurchaseOrderStatus poStatus) {
        this.poStatus = poStatus;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public List<PODetails> getPoDetailsList() {
        return poDetailsList;
    }

    public PurchaseOrderStatus getPoStatus() {
        return poStatus;
    }
}
