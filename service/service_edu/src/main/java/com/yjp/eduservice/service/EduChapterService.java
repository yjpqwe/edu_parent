package com.yjp.eduservice.service;

import com.yjp.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjp.eduservice.entity.chapter.ChapterVo;
import com.yjp.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-13
 */
public interface EduChapterService extends IService<EduChapter> {
    //根据课程ID查询章节小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节的方法
    boolean deleteChapter(String chapterId);
}
