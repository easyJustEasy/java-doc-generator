package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DocGenerateFactory {
    @Autowired
    private ApplicationContext context;
    public  IDocGenerate docGenerate(DocGenerateType type){
        return switch (type) {
            case OLLAMA_DEEP_SEEK -> context.getBean(OllamaDeepSeekTextGenerate.class);
            case TONGYI_AGENT -> context.getBean(TongYiAgentGenerate.class);
            default -> context.getBean(TongYiDocGenerate.class);
        };
    }

}
