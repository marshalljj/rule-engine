package com.majian.ruleengine.core;

import com.majian.ruleengine.ScriptExecuteException;

public class Rule {
    private int bizType;
    private int priority;
    private String ruleName;
    private Condition condition;
    private Action action;

    public Rule(int bizType, int priority, String ruleName, Condition condition, Action action) {
        this.bizType = bizType;
        this.priority = priority;
        this.ruleName = ruleName;
        this.condition = condition;
        this.action = action;
    }

    public int bizType() {
        return bizType;
    }

    public int priority() {
        return priority;
    }


    public String ruleName() {
        return ruleName;
    }

    public boolean match(Variables variables) {
        try {
            return condition.match(variables);
        } catch (Exception e) {
            String msg = String.format("something wrong on script for rule:$s bizType:%d", ruleName, bizType);
            throw new ScriptExecuteException(msg, e);
        }
    }

    public void execute(Variables variables) {
        action.execute(variables);//do not try-catch when exception
    }
}
