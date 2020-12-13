package com.majian.ruleengine.rule;

import com.majian.ruleengine.RpcProxy;
import com.majian.ruleengine.core.Rule;

import java.util.List;
import java.util.stream.Collectors;

public class RuleManager {
    private RpcProxy rpcProxy;
    private RuleConfigCache ruleConfigCache;
    private RuleConfigDao ruleConfigDao;

    public List<Rule> getRulesActive(int bizType) {
        List<RuleConfig> ruleConfigList = ruleConfigCache.getRulesActive(bizType);
        return ruleConfigList.stream()
                .map(this::buildRule)
                .collect(Collectors.toList());
    }

    private Rule buildRule(RuleConfig ruleConfig) {
        GroovyCondition groovyCondition = new GroovyCondition(ruleConfig.getRuleCondition());
        RpcAction rpcAction = new RpcAction(rpcProxy, ruleConfig.getActionUrl(), ruleConfig.getActionParam());
        return new Rule(ruleConfig.getBizType(), ruleConfig.getPriority(), ruleConfig.getRuleName(), groovyCondition, rpcAction);
    }

}
