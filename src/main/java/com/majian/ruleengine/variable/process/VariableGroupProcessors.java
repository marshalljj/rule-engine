package com.majian.ruleengine.variable.process;

import com.majian.ruleengine.Input;
import com.majian.ruleengine.variable.Variable;
import com.majian.ruleengine.variable.VariableConfig;

import java.util.List;

public class VariableGroupProcessors {
    private List<VariableGroupProcessor> variableGroupProcessorList;

    public List<Variable> process(String sourceType, List<VariableConfig> configList, Input input) {
        VariableGroupProcessor variableGroupProcessor = variableGroupProcessorList.stream()
                .filter(t -> t.sourceType().equalsIgnoreCase(sourceType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("unsupported sourceType: " + sourceType));
        return variableGroupProcessor.process(configList, input);
    }
}
