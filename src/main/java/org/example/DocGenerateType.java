package org.example;

public enum DocGenerateType {
    TONGYI("tongyi"),
    OLLAMA_DEEP_SEEK("ollama_deep_seek"),
    ;

    private final String type;

    DocGenerateType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
