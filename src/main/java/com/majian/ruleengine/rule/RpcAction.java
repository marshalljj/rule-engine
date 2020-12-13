package com.majian.ruleengine.rule;

import com.majian.ruleengine.RpcProxy;
import com.majian.ruleengine.RpcResponse;
import com.majian.ruleengine.UrlParser;
import com.majian.ruleengine.core.Action;
import com.majian.ruleengine.core.Variables;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

public class RpcAction implements Action {
    private RpcProxy rpcProxy;
    private String postUrl;
    private String requestBodyTemplate;

    public RpcAction(RpcProxy rpcProxy, String postUrl, String requestBodyTemplate) {
        this.rpcProxy = rpcProxy;
        this.postUrl = postUrl;
        this.requestBodyTemplate = requestBodyTemplate;
    }

    @Override
    public void execute(Variables variables) {
        if (StringUtils.isEmpty(postUrl)) {
            return;
        }
        UrlParser.RpcUrl rpcUrl = UrlParser.parse(postUrl);
        String serviceName = rpcUrl.getServiceName();
        String path = rpcUrl.getPath();
        String body = StringSubstitutor.replace(requestBodyTemplate, variables.asMap());
        assertBodyValid(body);
        RpcResponse rpcResponse = rpcProxy.post(serviceName, path, body);
        handleResponse(body, rpcResponse);
    }

    private void handleResponse(String body, RpcResponse rpcResponse) {
        //log
    }

    private void assertBodyValid(String body) {
        if (body.contains("${")) {
            String msg = String.format("执行action时缺少参数填充param:%s", body);
            throw new IllegalArgumentException(msg);
        }
    }
}
