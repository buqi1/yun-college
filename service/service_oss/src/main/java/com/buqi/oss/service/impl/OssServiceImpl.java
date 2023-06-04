package com.buqi.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.buqi.oss.service.OssService;
import com.buqi.oss.utils.ConstantYmlUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatarFile(MultipartFile file) {
        String endpoint = ConstantYmlUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantYmlUtils.KEY_ID;
        String accessKeySecret = ConstantYmlUtils.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantYmlUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件上传
            String fileName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();
            fileName = uuid+fileName;

            //可以进行添加目录      /2023/4/28/
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            fileName = dateTime+"/"+fileName;

            //调用oss上传文件
            ossClient.putObject(bucketName,fileName,inputStream);

            ossClient.shutdown();

            //手动拼接url
            //https://edu-buqi.oss-cn-beijing.aliyuncs.com/1.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
