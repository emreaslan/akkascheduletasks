package demo.datatypes;

import java.util.HashMap;
import java.util.Map;

public enum JobType {
    WRITE(1), NONE(0), READ(-1);

    private int value;
    private static Map<Integer, JobType> map = new HashMap<>();

    static {
        for (JobType job : JobType.values()) {
            map.put(job.getValue(), job);
        }
    }

    JobType(int value) {
        this.value = value;
    }

    public static JobType valueOf(int value) {
        return map.getOrDefault(value, null);
    }

    public int getValue() {
        return value;
    }
}