package com.buqi.oss.controller;

import com.buqi.oss.service.OssService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/fileoss")
    public R uploadOssFile(MultipartFile file){

        String url = ossService.uploadAvatarFile(file);
        return R.ok().data("url",url);
    }
}
