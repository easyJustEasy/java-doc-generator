package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocGenerateType {
    TONGYI("tongyi"),
    TONGYI_AGENT("tongyi_agent"),
    OLLAMA_DEEP_SEEK("ollama_deep_seek"),
    ;

    private final String type;

}
