package org.example;

import cn.hutool.core.util.StrUtil;

public class AppConfig {
    public static synchronized String apiKey() throws Exception {
        String dashscopeApiKey = System.getenv("DASHSCOPE_API_KEY");
        if (StrUtil.isBlankIfStr(dashscopeApiKey)) {
            throw new RuntimeException("百炼APIKey为空！请先设置");
        }
        return dashscopeApiKey;
    }

    public static String agentId() {
        String dashscopeApiKey = System.getenv("JAVA_AGENT_ID");
        if (StrUtil.isBlankIfStr(dashscopeApiKey)) {
            throw new RuntimeException("JAVA_AGENT_ID为空！请先设置");
        }
        return dashscopeApiKey;
    }
}
