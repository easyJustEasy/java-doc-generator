package org.example;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

public class DeepSeekUtil {
    public static String removeThink(String storyText) {
        if (StrUtil.isBlankIfStr(storyText)) {
            return storyText;
        }
        if (storyText.contains("<think>") && storyText.contains("</think>")) {
            storyText = storyText.substring(storyText.indexOf("</think>") + "</think>".length());
        }
        return storyText;
    }
}
