package com.majian.ruleengine;

import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Map;

public class UrlParser {

    private UrlParser() {
    }

    public static RpcUrl parse(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        int serviceEnd = url.indexOf("/");
        String serviceName = url.substring(0, serviceEnd);
        String pathWithParam = url.substring(serviceEnd);

        int paramBegin = pathWithParam.indexOf("?");
        if (paramBegin == -1) {
            return new RpcUrl(serviceName, pathWithParam, new HashMap<>());
        }else {
            String path = pathWithParam.substring(0, paramBegin);
            String paramString = pathWithParam.substring(paramBegin + 1);
            Map<String,String> param = stringToParam(paramString);
            return new RpcUrl(serviceName, path, param);
        }
    }

    private static Map<String, String> stringToParam(String paramString) {
        if (paramString.isEmpty()) {
            return new HashMap<>();
        }else {
            return Splitter.on("&").withKeyValueSeparator("=").split(paramString);
        }
    }

    public static class RpcUrl {
        private String serviceName;
        private String path;
        private Map<String, String> params;

        public RpcUrl(String serviceName, String path, Map<String, String> params) {
            this.serviceName = serviceName;
            this.path = path;
            this.params = params;
        }

        public String getServiceName() {
            return serviceName;
        }

        public String getPath() {
            return path;
        }

        public Map<String, String> getParams() {
            return params;
        }
    }
}
