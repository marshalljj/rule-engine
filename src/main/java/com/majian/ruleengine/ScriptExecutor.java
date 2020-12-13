package com.majian.ruleengine;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptExecutor {
    private ScriptExecutor() {
    }

    private static final Map<String, Script> cache = new ConcurrentHashMap<>();

    public static Object execute(String scriptStr, Map<String,Object> context) {
        Binding binding = new Binding(context);
        Script script = cache.computeIfAbsent(scriptStr, t -> new GroovyShell().parse(t));
        return InvokerHelper.createScript(script.getClass(), binding).run();
    }
}
