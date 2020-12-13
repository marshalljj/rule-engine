package com.majian.ruleengine.core;

import java.util.Map;

public interface Variables {
    void put(String name, Object value);
    Map<String,Object> asMap();
}
