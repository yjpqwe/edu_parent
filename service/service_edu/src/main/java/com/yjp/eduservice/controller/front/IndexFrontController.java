package com.yjp.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjp.commonutils.R;
import com.yjp.eduservice.entity.EduCourse;
import com.yjp.eduservice.entity.EduTeacher;
import com.yjp.eduservice.service.EduCourseService;
import com.yjp.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;
    //查询前八条热门课程 查询前4条名师
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> eduCourseList = courseService.list(wrapperCourse);

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("eduList",eduCourseList).data("teacherList",eduTeacherList);
    }
}
