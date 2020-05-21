package com.yjp.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjp.eduservice.entity.EduChapter;
import com.yjp.eduservice.entity.EduCourse;
import com.yjp.eduservice.entity.EduVideo;
import com.yjp.eduservice.entity.chapter.ChapterVo;
import com.yjp.eduservice.entity.chapter.VideoVo;
import com.yjp.eduservice.entity.vo.CourseInfoVo;
import com.yjp.eduservice.mapper.EduChapterMapper;
import com.yjp.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjp.eduservice.service.EduVideoService;
import com.yjp.servicebase.exceptionhandler.YjpException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-13
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2.根据id查询所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //封装之前，创建一个list集合，用于封装最终数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3.遍历查询章节list集合进行封装

        for(EduChapter eduChapter : eduChapterList){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);
            //4.遍历查询小节list集合，进行封装
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo: eduVideoList){
                VideoVo videoVo = new VideoVo();
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //进行封装
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询有没有小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(queryWrapper);
        //判断
        if(count>0){
            //不进行删除
            throw new YjpException(20001,"不能删除");
        }else{
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }

    }
}
