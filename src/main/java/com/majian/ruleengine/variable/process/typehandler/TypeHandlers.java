package com.majian.ruleengine.variable.process.typehandler;

import java.util.List;

public class TypeHandlers {
    private List<TypeHandler> typeHandlerList;

    public Object handle(String value, String dataType) {
        TypeHandler typeHandler = typeHandlerList.stream()
                .filter(t -> t.supportType().equalsIgnoreCase(dataType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unsupported type: " + dataType));
        return typeHandler.parse(value);
    }
}
