package com.guotion.pushserver.client.message;

import java.util.HashMap;
import java.util.Map;

public class MapMessage extends NetMessage {

    HashMap<String, Object> data;

    public MapMessage() {
        this.data = new HashMap();
    }

    public int getState() {
        return ((Integer) this.data.get("state")).intValue();
    }

    public Map getDatas() {
        return this.data;
    }

    public void setState(int state) {
        this.data.put("state", Integer.valueOf(state));
    }

    public void putData(String key, Object value) {
        this.data.put(key, value);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }
}