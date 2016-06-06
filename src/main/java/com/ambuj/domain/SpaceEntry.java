package com.ambuj.domain;

import java.util.Map;

/**
 * Created by Aj on 03-06-2016.
 */
public class SpaceEntry {
    private String key;
    private Object value;

    public SpaceEntry() {
    }

    public SpaceEntry(Map.Entry<String, Object> entry) {
        key = entry.getKey();
        value = entry.getValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
