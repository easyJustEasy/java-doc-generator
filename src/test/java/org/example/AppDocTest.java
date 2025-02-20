package org.example;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AppDocTest {
    @Autowired
    OllamaApi ollamaApi;
    @Autowired
    private AppDocGenerate appDocGenerate;
    @Test
    public void test1() throws Exception {

        Flux<OllamaApi.ProgressResponse> progressResponseFlux = ollamaApi.pullModel(new OllamaApi.PullModelRequest("deepseek-r1:14b"));

    }
    @Test
    public void test() throws Exception {
        File touch = FileUtil.touch("temp/" + 1 + ".md");
        System.out.println(touch.getAbsolutePath());

    }

    @Test
    public void test3() throws Exception {

        List<DocWrapper> docDTOS = appDocGenerate.batchGenerate(30, DocGenerateType.TONGYI_AGENT);
        for (DocWrapper docDTO : docDTOS) {
            if (docDTO==null||docDTO.getDocDTO()==null|| StrUtil.isBlankIfStr(docDTO.getDocDTO().getContent())) {
                continue;
            }
            FileUtil.writeString(docDTO.getDocDTO().getContent(),FileUtil.touch("temp/"+TitleUtil.sub( docDTO.getTitle()) +".md"), StandardCharsets.UTF_8);
        }
    }
}