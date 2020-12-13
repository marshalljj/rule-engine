package com.majian.ruleengine;

import com.majian.ruleengine.core.Rule;
import com.majian.ruleengine.core.RuleEngine;
import com.majian.ruleengine.core.Variables;
import com.majian.ruleengine.rule.RuleManager;
import com.majian.ruleengine.variable.VariableResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RuleEngineApi {
    private static final Logger log = LoggerFactory.getLogger(RuleEngineApi.class);
    private RuleManager ruleManager;
    private RuleEngine ruleEngine;
    private VariableResolver variableResolver;

    public void fire(int bizType, Input input, boolean fireFirst) {
        List<Rule> rules = ruleManager.getRulesActive(bizType);
        Variables variables = variableResolver.processActive(bizType, input);
        ruleEngine.fire(rules,variables, fireFirst);
    }

    public List<String> match(int bizType, Input input) {
        List<Rule> rules = ruleManager.getRulesActive(bizType);
        Variables variables = variableResolver.processActive(bizType, input);
        return ruleEngine.match(rules, variables);
    }

    private void logVariables(Variables variables) {
        log.info("variables:{}", variables.asMap());
    }
}
