package com.buqi.eduservice.controller;


import com.buqi.eduservice.entity.EduChapter;
import com.buqi.eduservice.entity.vo.ChapterVO;
import com.buqi.eduservice.service.EduChapterService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Past;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("/chaptervideo/{id}")
    public R getAllChapterVideo(@PathVariable String id){

        List<ChapterVO> list = chapterService.getAllChapterVideoList(id);
        return R.ok().data("ChapterVideoList",list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    //根据id查询章节
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节

    /**
     * 1.如果章节没有小节，就直接删除
     * 2。如果章节有小节，就不能直接删除
     */
    @DeleteMapping("/{id}")
    public R deleteChapter(@PathVariable String id){
        chapterService.deleteChapter(id);
        return R.ok();
    }
}

