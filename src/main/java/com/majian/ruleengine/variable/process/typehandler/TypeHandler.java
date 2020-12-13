package com.majian.ruleengine.variable.process.typehandler;

public interface TypeHandler {
    String supportType();
    Object parse(String str);
}
