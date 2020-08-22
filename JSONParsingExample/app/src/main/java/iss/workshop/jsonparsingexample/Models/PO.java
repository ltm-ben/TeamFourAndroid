package iss.workshop.jsonparsingexample.Models;

import java.util.Date;

public class PO {

    private int id;

    private String orderDate;

    private String supplierName;

    private PurchaseOrderStatus status;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }
}
