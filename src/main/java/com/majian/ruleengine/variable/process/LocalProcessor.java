package com.majian.ruleengine.variable.process;

import com.majian.ruleengine.Input;
import com.majian.ruleengine.ScriptExecuteException;
import com.majian.ruleengine.ScriptExecutor;
import com.majian.ruleengine.variable.Variable;
import com.majian.ruleengine.variable.VariableConfig;

import java.util.List;
import java.util.stream.Collectors;

public class LocalProcessor implements VariableGroupProcessor{
    @Override
    public String sourceType() {
        return "local";
    }

    @Override
    public List<Variable> process(List<VariableConfig> variableConfigList, Input input) {
        return variableConfigList.stream()
                .map(config -> {
                    try {
                        Object value = ScriptExecutor.execute(config.getScript(), input.asMap());
                        return new Variable(config.getVarName(), value);
                    } catch (Exception e) {
                        String msg = String.format("something wrong on script or input for var:%s biz:%d", config.getVarName(), config.getBizType());
                        throw new ScriptExecuteException(msg,e);
                    }
                }).collect(Collectors.toList());
    }
}
