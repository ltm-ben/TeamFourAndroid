package iss.workshop.jsonparsingexample.Models;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    Clip(1),
    Envelope(2),
    Eraser(3),
    Exercise(4),
    File(5),
    Pen(6),
    Puncher(7),
    Pad(8),
    Paper(9),
    Ruler(10),
    Scissors(11),
    Tape(12),
    Sharpener(13),
    Shorthand(14),
    Stapler(15),
    Tacks(16),
    Tparency(17),
    Tray(18);

    private int value;
    private static Map map = new HashMap<>();

    private Category(int value) {
        this.value = value;
    }

    static {
        for (Category category : Category.values()) {
            map.put(category.value, category);
        }
    }

    public static Category valueOf(int category) {
        return (Category) map.get(category);
    }

    public int getValue() {
        return value;
    }
}
