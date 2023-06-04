package com.buqi.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduservice.entity.EduSubject;
import com.buqi.eduservice.entity.excel.SubjectData;
import com.buqi.eduservice.entity.subject.OneSubject;
import com.buqi.eduservice.entity.subject.TwoSubject;
import com.buqi.eduservice.listener.ExcelListener;
import com.buqi.eduservice.mapper.EduSubjectMapper;
import com.buqi.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-04-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new ExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAll() {
        //1.获取全部一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //2.获取全部二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //最后的集合
        List<OneSubject> finalSubject = new ArrayList<>();
        //3.将得到的一级分类放到最后的集合
        /**
         * 将得到的oneSubjectList编历出来，再将其复制给oneSubject，最后在让最后的集合进行添加
         * */

        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubject.add(oneSubject);

            //对二级分类进行封装
            //创建集合将二级分类进行封装
            List<TwoSubject> twoFinalSubject = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject eduSubject2 = twoSubjectList.get(m);
                if (eduSubject2.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2,twoSubject);
                    twoFinalSubject.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubject);
        }
        return finalSubject;
    }
}
