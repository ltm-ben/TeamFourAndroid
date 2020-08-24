package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum DisbursementStatus {

    PENDING_PACKING(0),
    PENDING_DISBURSEMENT(1),
    ACKNOWLEDGED(2);

    private int value;
    private static Map map = new HashMap<>();

    private DisbursementStatus(int value) {
        this.value = value;
    }

    static {
        for (DisbursementStatus disbursementStatus : DisbursementStatus.values()) {
            map.put(disbursementStatus.value, disbursementStatus);
        }
    }

    public static DisbursementStatus valueOf(int disbursementStatus) {
        return (DisbursementStatus) map.get(disbursementStatus);
    }

    public int getValue() {
        return value;
    }
}
