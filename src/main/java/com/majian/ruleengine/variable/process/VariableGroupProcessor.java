package com.majian.ruleengine.variable.process;

import com.majian.ruleengine.Input;
import com.majian.ruleengine.variable.Variable;
import com.majian.ruleengine.variable.VariableConfig;

import java.util.List;

public interface VariableGroupProcessor {
    String sourceType();
    List<Variable> process(List<VariableConfig> variableConfigList, Input input);
}
