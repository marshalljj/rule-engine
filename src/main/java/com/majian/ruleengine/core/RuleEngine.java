package com.majian.ruleengine.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RuleEngine {
    private static final Logger log = LoggerFactory.getLogger(RuleEngine.class);

    public void fire(List<Rule> rules, Variables variables, boolean fireFirst) {
        rules.sort(Comparator.comparing(Rule::priority));

        for (Rule rule : rules) {
            if (rule.match(variables)) {
                log.info(" hit rule:{} in biz:{}, execute action", rule.ruleName(), rule.bizType());
                rule.execute(variables);
                if (fireFirst) {
                    return;
                }
            }
        }
    }

    public List<String> match(List<Rule> rules, Variables variables) {
        return rules.stream().sorted(Comparator.comparing(Rule::priority))
                .filter(rule -> rule.match(variables))
                .map(Rule::ruleName)
                .collect(Collectors.toList());
    }
}
