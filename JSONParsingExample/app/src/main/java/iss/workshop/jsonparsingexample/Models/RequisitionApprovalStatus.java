package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum RequisitionApprovalStatus {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private int value;
    private static Map map = new HashMap<>();

    private RequisitionApprovalStatus(int value) {
        this.value = value;
    }

    static {
        for (RequisitionApprovalStatus requisitionApprovalStatus : RequisitionApprovalStatus.values()) {
            map.put(requisitionApprovalStatus.value, requisitionApprovalStatus);
        }
    }

    public static RequisitionApprovalStatus valueOf(int requisitionApprovalStatus) {
        return (RequisitionApprovalStatus) map.get(requisitionApprovalStatus);
    }

    public int getValue() {
        return value;
    }
}
