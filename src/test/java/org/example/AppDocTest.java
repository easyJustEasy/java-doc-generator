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

import javax.swing.text.DateFormatter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;


@SpringBootTest(classes = App.class)
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
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 300; i++) {
            String parent = "E:\\java-article"+File.separator+date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<DocWrapper> docDTOS = appDocGenerate.batchGenerate(47, DocGenerateType.TONGYI);
            for (DocWrapper docDTO : docDTOS) {
                if (docDTO==null||docDTO.getDocDTO()==null|| StrUtil.isBlankIfStr(docDTO.getDocDTO().getContent())) {
                    continue;
                }
                FileUtil.writeString(docDTO.getDocDTO().getContent(),FileUtil.touch(parent+File.separator+TitleUtil.sub( docDTO.getTitle()) +".md"), StandardCharsets.UTF_8);

                log.info("生成成功：{}",docDTO.getTitle());
            }
            date = date.plusDays(1);
        }

    }
}