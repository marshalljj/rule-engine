package com.majian.ruleengine.rule;

import java.util.List;

public interface RuleConfigCache {
    List<RuleConfig> getRulesActive(int bizType);
}
