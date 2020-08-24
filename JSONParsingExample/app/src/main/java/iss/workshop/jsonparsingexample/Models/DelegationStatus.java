package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum DelegationStatus {
    CANCELLED(0), EXTENDED(1), SELECTED(2);

    private int value;
    private static Map map = new HashMap<>();

    private DelegationStatus(int value) {
        this.value = value;
    }

    static {
        for (DelegationStatus delegationStatus : DelegationStatus.values()) {
            map.put(delegationStatus.value, delegationStatus);
        }
    }

    public static DelegationStatus valueOf(int delegationStatus) {
        return (DelegationStatus) map.get(delegationStatus);
    }

    public int getValue() {
        return value;
    }
}
