package com.buqi.eduservice.service;

import com.buqi.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buqi.eduservice.entity.vo.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getAllChapterVideoList(String id);

    void deleteChapter(String id);

    void deleteById(String courseId);
}
