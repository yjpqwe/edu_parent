package com.yjp.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjp.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjp.eduservice.entity.frontvo.CourseFrontVo;
import com.yjp.eduservice.entity.vo.CourseInfoVo;
import com.yjp.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-13
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);
    //条件查询带分页查询课程前台
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
}
