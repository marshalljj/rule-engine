package com.majian.ruleengine.variable.process;

import com.majian.ruleengine.*;
import com.majian.ruleengine.variable.Variable;
import com.majian.ruleengine.variable.VariableConfig;
import com.majian.ruleengine.variable.process.typehandler.TypeHandlers;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoteProcessor implements VariableGroupProcessor{
    private static final Logger log = LoggerFactory.getLogger(RemoteProcessor.class);
    private RpcProxy rpcProxy;
    private TypeHandlers typeHandlers;

    @Override
    public String sourceType() {
        return "remote";
    }

    @Override
    public List<Variable> process(List<VariableConfig> variableConfigList, Input input) {
        Map<String, List<VariableConfig>> groupByUrl = variableConfigList.stream()
                .collect(Collectors.groupingBy(VariableConfig::getUrl));
        return groupByUrl.entrySet()
                .parallelStream()
                .flatMap(r -> {
                    String url = r.getKey();
                    Object responseContent = processRequest(url, input);
                    return r.getValue().stream()
                            .map(config -> toVariable(config, responseContent));
                }).collect(Collectors.toList());
    }

    private Variable toVariable(VariableConfig config, Object responseContent) {
        if (responseContent == null) {
            Object value = typeHandlers.handle(config.getDefaultValue(), config.getDataType());
            return new Variable(config.getVarName(), value);
        }
        HashMap<String, Object> context = new HashMap<>();
        context.put("content", responseContent);
        Object value = null;

        try {
            value = ScriptExecutor.execute(config.getScript(), context);
        } catch (Exception e) {
            String msg = String.format("something wrong on script or responseContent for var:%s bizType:%d", config.getVarName(), config.getBizType());
            throw new ScriptExecuteException(msg, e);
        }

        if (value == null) {
            value = typeHandlers.handle(config.getDefaultValue(), config.getDataType());
        }

        return new Variable(config.getVarName(), value);
    }

    private Object processRequest(String urlTemplate, Input input) {
        String url = StringSubstitutor.replace(urlTemplate, input.asMap());
        assertUrlValid(url);

        UrlParser.RpcUrl rpcUrl = UrlParser.parse(url);
        String serviceName = rpcUrl.getServiceName();
        String path = rpcUrl.getPath();
        Map<String, String> params = rpcUrl.getParams();

        RpcResponse rpcResponse = null;
        try {
            rpcResponse = rpcProxy.get(serviceName, path, params);
        } catch (Exception e) {
            log.error("call rpc failed request:{}",url, e);
            return null;
        }

        return rpcResponse.getContent();
    }

    private void assertUrlValid(String url) {
        if (url.contains("${")){
            String msg = String.format("获取变量时缺少参数填充url:%s", url);
            throw new IllegalArgumentException(msg);
        }
    }
}
