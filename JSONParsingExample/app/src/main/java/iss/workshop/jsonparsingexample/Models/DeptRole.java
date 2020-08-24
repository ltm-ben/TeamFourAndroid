package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum DeptRole {
    EMPLOYEE(1),
    DEPT_HEAD(2),
    CONTACT(3),
    DEPT_REP(4),
    STORE_CLERK(5),
    STORE_SUPERVISOR(6),
    STORE_MANAGER(7),
    DELEGATED_EMPLOYEE(8);

    private int value;
    private static Map map = new HashMap<>();

    private DeptRole(int value) {
        this.value = value;
    }

    static {
        for (DeptRole deptrole : DeptRole.values()) {
            map.put(deptrole.value, deptrole);
        }
    }

    public static DeptRole valueOf(int deptrole) {
        return (DeptRole) map.get(deptrole);
    }

    public int getValue() {
        return value;
    }


}
