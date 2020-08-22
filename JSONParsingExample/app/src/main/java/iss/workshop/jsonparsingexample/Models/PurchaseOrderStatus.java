package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum PurchaseOrderStatus {

    //TO_BE_PROCESSED(0),
    //PARTIAL(1),
    //FULFILLED(2);

    Edited(0),
    Submitted(1),
    Processing(2),
    Completed(3),
    Cancelled(4);

    private int value;
    private static Map map = new HashMap<>();

    private PurchaseOrderStatus(int value) {
        this.value = value;
    }

    static {
        for (PurchaseOrderStatus requisitionFulfillmentStatus : PurchaseOrderStatus.values()) {
            map.put(requisitionFulfillmentStatus.value, requisitionFulfillmentStatus);
        }
    }

    public static PurchaseOrderStatus valueOf(int requisitionFulfillmentStatus) {
        return (PurchaseOrderStatus) map.get(requisitionFulfillmentStatus);
    }

    public int getValue() {
        return value;
    }
}
