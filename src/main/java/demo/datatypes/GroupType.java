package demo.datatypes;

import java.util.HashMap;
import java.util.Map;

public enum GroupType {
    INSERT(1), UPDATE(2), COMPUTE(-1), EVALUATE(-2);

    private int value;
    private static Map<Integer, GroupType> map = new HashMap<>();

    static {
        for (GroupType group : GroupType.values()) {
            map.put(group.getValue(), group);
        }
    }

    GroupType(int value) {
        this.value = value;
    }

    public static GroupType valueOf(int value) {
        return map.getOrDefault(value, null);
    }

    public int getValue() {
        return value;
    }
}