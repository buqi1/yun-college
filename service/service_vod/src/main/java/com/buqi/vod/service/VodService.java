package com.buqi.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {


    void deleteBatch(List<String> videoList);

    String uploadVideo(MultipartFile file);
}
