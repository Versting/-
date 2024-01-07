package com.twotwo.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OssClient {
    private final OSS oss;
    private final String bucketName = "jkmzj";
    private final String endPoint = "oss-cn-chengdu.aliyuncs.com";

    public OssClient() {
        oss = new OSSClientBuilder().build(endPoint, "LTAI5t9qHnGgUY47oWbeiPUQ", "mEeZrgVvh3F8SexodcpREWqZ1GxmRh");
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        String key = IdUtil.fastUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(endPoint, "LTAI5t9qHnGgUY47oWbeiPUQ", "mEeZrgVvh3F8SexodcpREWqZ1GxmRh");
        client.putObject(bucketName, key, multipartFile.getInputStream());
        client.shutdown();
        return key;
    }

    public String getUrl(String filename) {
        return StrUtil.format("https://{}.{}/{}", bucketName, endPoint, filename);
    }

    public byte[] downloadFile(String url) throws IOException {
        OSS client = new OSSClientBuilder().build(endPoint, "LTAI5t9qHnGgUY47oWbeiPUQ", "mEeZrgVvh3F8SexodcpREWqZ1GxmRh");
        try (InputStream inputStream = client.getObject(bucketName, url).getObjectContent();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        } finally {
            client.shutdown();
        }
    }

}
