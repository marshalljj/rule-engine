package com.majian.ruleengine.variable;

public class Variable {
    private String name;
    private Object value;

    public Variable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
