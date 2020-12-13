package com.majian.ruleengine.variable;

import com.majian.ruleengine.Input;
import com.majian.ruleengine.core.Variables;
import com.majian.ruleengine.variable.process.VariableGroupProcessors;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VariableResolver {
    private VariableConfigCache variableConfigCache;
    private VariableGroupProcessors groupProcessors;

    public Variables fetchActive(int bizType, Input input) {
        List<VariableConfig> variableConfigList = variableConfigCache.getActive(bizType);
        Map<String, List<VariableConfig>> groupBySourceType = variableConfigList.stream()
                .collect(Collectors.groupingBy(VariableConfig::getSourceType));

        List<Variable> variableValues = groupBySourceType.entrySet()
                .stream()
                .map(entry -> groupProcessors.process(entry.getKey(), entry.getValue(), input))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Variables variables = new VariablesImpl();
        for (Variable variable : variableValues) {
            variables.put(variable.getName(), variable.getValue());
        }
        return variables;
    }
}
