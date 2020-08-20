package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum RequisitionFulfillmentStatus {

    TO_BE_PROCESSED(0),
    PARTIAL(1),
    FULFILLED(2);

    private int value;
    private static Map map = new HashMap<>();

    private RequisitionFulfillmentStatus(int value) {
        this.value = value;
    }

    static {
        for (RequisitionFulfillmentStatus requisitionFulfillmentStatus : RequisitionFulfillmentStatus.values()) {
            map.put(requisitionFulfillmentStatus.value, requisitionFulfillmentStatus);
        }
    }

    public static RequisitionFulfillmentStatus valueOf(int requisitionFulfillmentStatus) {
        return (RequisitionFulfillmentStatus) map.get(requisitionFulfillmentStatus);
    }

    public int getValue() {
        return value;
    }
}
