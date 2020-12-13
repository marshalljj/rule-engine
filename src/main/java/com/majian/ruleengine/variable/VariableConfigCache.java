package com.majian.ruleengine.variable;

import java.util.List;

public interface VariableConfigCache {
    List<VariableConfig> getActive(int bizType);
}
