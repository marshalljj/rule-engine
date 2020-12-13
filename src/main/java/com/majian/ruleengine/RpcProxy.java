package com.majian.ruleengine;

import com.majian.ruleengine.RpcResponse;

import java.util.Map;

public interface RpcProxy {
    RpcResponse post(String serviceName, String path, String body);

    RpcResponse get(String serviceName,  String path, Map<String, String> params);
}
