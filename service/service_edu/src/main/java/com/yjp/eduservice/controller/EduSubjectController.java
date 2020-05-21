package com.yjp.eduservice.controller;


import com.yjp.commonutils.R;
import com.yjp.eduservice.entity.subject.OneSubject;
import com.yjp.eduservice.mapper.EduSubjectMapper;
import com.yjp.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-10
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();

        return R.ok().data("list",list);
    }

}

