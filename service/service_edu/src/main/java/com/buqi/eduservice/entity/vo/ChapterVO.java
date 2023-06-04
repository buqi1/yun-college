package com.buqi.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVO {

    private String title;
    private String id;

    List<VideoVO> children = new ArrayList<>();
}
