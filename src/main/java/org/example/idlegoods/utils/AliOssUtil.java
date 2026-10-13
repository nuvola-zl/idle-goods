package org.example.idlegoods.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public class AliOssUtil {

    // 从环境变量读取，避免密钥硬编码进代码
    private static final String ENDPOINT = System.getenv().getOrDefault(
            "ALIYUN_OSS_ENDPOINT", "https://oss-cn-hangzhou.aliyuncs.com");
    private static final String ACCESS_KEY_ID = System.getenv("ALIYUN_ACCESS_KEY_ID");
    private static final String ACCESS_KEY_SECRET = System.getenv("ALIYUN_ACCESS_KEY_SECRET");
    private static final String BUCKET_NAME = System.getenv().getOrDefault(
            "ALIYUN_OSS_BUCKET_NAME", "my-bucket");

    static {
        if (ACCESS_KEY_ID == null || ACCESS_KEY_SECRET == null) {
            throw new IllegalStateException(
                    "请配置环境变量 ALIYUN_ACCESS_KEY_ID 和 ALIYUN_ACCESS_KEY_SECRET");
        }
    }
    /**
     * 上传头像图片到阿里云OSS
     * @param file 前端上传的图片文件
     * @return 阿里云可访问的图片URL
     * @throws Exception 上传异常
     */
    public static String upload(MultipartFile file) throws Exception {
        // 1. 创建OSS客户端
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        // 2. 生成唯一文件名（避免重名）
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = "idle-goods-avatar/" + UUID.randomUUID() + suffix;

        // 3. 上传文件到OSS
        ossClient.putObject(
                BUCKET_NAME,
                fileName,
                file.getInputStream()
        );

        // 4. 关闭OSS客户端
        ossClient.shutdown();

        // 5. 返回可访问的图片URL
        return "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + fileName;
    }
}