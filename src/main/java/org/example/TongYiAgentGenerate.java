package org.example;

import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class TongYiAgentGenerate implements IDocGenerate{
    @Override
    public String generate(String system, String prompt) throws Exception {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(system)
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(prompt)
                .build();
        ApplicationParam param = ApplicationParam.builder()
                .apiKey(AppConfig.apiKey())
                .appId(AppConfig.agentId())
                .messages(Arrays.asList(systemMsg, userMsg))
                .build();

        Application application = new Application();
        ApplicationResult result = application.call(param);
        log.info("TongYiAgentGenerate result:{}", JSONObject.toJSONString(result));
        return  result.getOutput().getText();
    }

}