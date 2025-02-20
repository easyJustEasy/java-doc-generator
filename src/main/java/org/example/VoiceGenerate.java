package org.example;


import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

public class VoiceGenerate {
    private static String model = "cosyvoice-v1";
    private static String voice = "longyue";
    private  int count = 0;
    public  String generate(String text,String filePath) throws Exception {
        SpeechSynthesisParam param =
                SpeechSynthesisParam.builder()
                        // 若没有将API Key配置到环境变量中，需将下面这行代码注释放开，并将apiKey替换为自己的API Key
                         .apiKey(AppConfig.apiKey())
                        .model(model)
                        .voice(voice)
                        .speechRate(.7F)
                        .build();
        SpeechSynthesizer synthesizer =new SpeechSynthesizer(param,null);
        ByteBuffer audio = synthesizer.call(text);
        if (audio==null&&count++<3) {
            return generate(text,filePath);
        }

        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(audio.array());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        count  = 0;
        return file.getAbsolutePath();


    }
}
