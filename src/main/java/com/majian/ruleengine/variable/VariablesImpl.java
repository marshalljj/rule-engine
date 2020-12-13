package com.majian.ruleengine.variable;

import com.google.common.collect.ImmutableMap;
import com.majian.ruleengine.core.Variables;

import java.util.HashMap;
import java.util.Map;

public class VariablesImpl implements Variables {
    private Map<String,Object> values = new HashMap<>();

    @Override
    public void put(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public Map<String, Object> asMap() {
        return ImmutableMap.copyOf(values);
    }
}
