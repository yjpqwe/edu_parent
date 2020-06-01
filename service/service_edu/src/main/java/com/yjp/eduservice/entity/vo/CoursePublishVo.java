package com.yjp.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {
    private String title;
    private String cover;
    private Integer lessonNum;
    private String oneSubject;
    private String twoSubject;
    private String teacherName;
    private String price;//只用于显示
}
