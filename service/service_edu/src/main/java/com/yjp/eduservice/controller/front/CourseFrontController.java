package com.yjp.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjp.commonutils.R;
import com.yjp.eduservice.entity.EduCourse;
import com.yjp.eduservice.entity.frontvo.CourseFrontVo;
import com.yjp.eduservice.service.EduChapterService;
import com.yjp.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    //注:required 为false，表示，后面可以没有值，没有值不会报错
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }
}
