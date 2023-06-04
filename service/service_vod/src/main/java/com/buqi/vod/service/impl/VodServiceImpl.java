package com.buqi.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.buqi.handler.GuliException;
import com.buqi.util.R;
import com.buqi.vod.service.VodService;
import com.buqi.vod.utils.InitVodClient;
import com.buqi.vod.utils.VodUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService{

    //删除多个视频
    @Override
    public void deleteBatch(List<String> videoList) {
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.KEY_ID, VodUtils.KEY_SECRET);
            //创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //request.setVideoIds(1,2,3,4...)       将videoList转换成这种形式1,2,3,4...
            String ids = StringUtils.join(videoList.toArray(), ",");
            //设置id值
            request.setVideoIds(ids);
            //调用client方法删除视频
            client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //上传视频
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //文件的原始名称
            String fileName = file.getOriginalFilename();
            //1232323.1.11.mp4将最后面的.mp4删除，，上传的名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            //将异常try,catch
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VodUtils.KEY_ID, VodUtils.KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId = null;
            if (response.isSuccess()) {
               videoId = response.getVideoId();
               return videoId;
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                return videoId;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
