package com.majian.ruleengine.rule;

import com.majian.ruleengine.ScriptExecutor;
import com.majian.ruleengine.core.Condition;
import com.majian.ruleengine.core.Variables;

public class GroovyCondition implements Condition {
    private String scriptStr;

    public GroovyCondition(String scriptStr) {
        this.scriptStr = scriptStr;
    }

    @Override
    public boolean match(Variables variables) {
        return (boolean) ScriptExecutor.execute(scriptStr, variables.asMap());
    }
}
