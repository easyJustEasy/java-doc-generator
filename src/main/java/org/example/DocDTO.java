package org.example;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DocDTO {
    private String originalPrompt;
    private String userMessage;
    private String systemMessage;
    private String content;
    private boolean success;
    private String errorMessage;
}
