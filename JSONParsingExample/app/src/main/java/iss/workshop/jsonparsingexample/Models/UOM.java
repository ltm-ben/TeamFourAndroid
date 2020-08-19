package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum UOM {
    Dozen(1),
    Box(2),
    Each(3),
    Set(4),
    Packet(5);

    private int value;
    private static Map map = new HashMap<>();

    private UOM(int value) {
        this.value = value;
    }

    static {
        for (UOM uom : UOM.values()) {
            map.put(uom.value, uom);
        }
    }

    public static UOM valueOf(int uom) {
        return (UOM) map.get(uom);
    }

    public int getValue() {
        return value;
    }
}
