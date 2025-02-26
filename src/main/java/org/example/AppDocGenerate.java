package org.example;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AppDocGenerate {
    @Autowired
    private DocGenerateFactory docGenerateFactory;

    private String getSystem() {
        return DocConsts.system;
    }

    private String getUserPrompt(String prompt) {
        return prompt + DocConsts.tail;
    }

    public DocDTO generate(DocGenerateType docGenerateType, String prompt) throws Exception {
        IDocGenerate iDocGenerate = docGenerateFactory.docGenerate(docGenerateType);
        if (iDocGenerate == null) {
            throw new RuntimeException("docGenerateType is not support");
        }
        return generate(iDocGenerate, prompt);
    }

    private DocDTO generate(IDocGenerate iDocGenerate, String prompt) {
        String system = getSystem();
        String userPrompt = getUserPrompt(prompt);
        DocDTO docDTO = new DocDTO();
        docDTO.setSuccess(true);
        String generate = null;
        try {
            generate = iDocGenerate.generate(system, userPrompt);
        } catch (Exception e) {
            log.error("error when generate :" + ExceptionUtil.getRootCause(e));
            docDTO.setSuccess(false);
            docDTO.setErrorMessage(e.getMessage());
        }
        docDTO.setContent(generate);
        docDTO.setOriginalPrompt(prompt);
        docDTO.setSystemMessage(system);
        docDTO.setUserMessage(userPrompt);
        return docDTO;
    }

    public List<DocDTO> generate(DocGenerateType docGenerateType, List<String> prompt) throws Exception {
        IDocGenerate iDocGenerate = docGenerateFactory.docGenerate(docGenerateType);
        if (iDocGenerate == null) {
            throw new RuntimeException("docGenerateType is not support");
        }
        return prompt.stream().map(p -> generate(iDocGenerate, p)).toList();
    }

    public List<DocWrapper> batchGenerate(int docNum, DocGenerateType docGenerateType) throws Exception {
        IDocGenerate iDocGenerate = docGenerateFactory.docGenerate(docGenerateType);
        if (iDocGenerate == null) {
            throw new RuntimeException("docGenerateType is not support");
        }
        String system = "请生成" + docNum + "个java技术方向，帮我找一下灵感,我只需要一个标题，不要重复，用@@@@@@隔开";
        String generate = iDocGenerate.generate(DocConsts.titleSystem, system);
        String[] split = generate.split("@@@@@@");
        // 自定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<CompletableFuture<DocWrapper>> cf = Arrays.stream(split).filter(StrUtil::isNotBlank).map(p -> {
           return CompletableFuture.supplyAsync(() -> {
               DocDTO generate1 = generate(iDocGenerate, p);
               return new DocWrapper(p, generate1);
           },executorService);
        }).toList();
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(cf.toArray(new CompletableFuture[0]));
        combinedFuture.get(3, TimeUnit.HOURS);
        return cf.stream().map(CompletableFuture::join).toList();
    }
}
