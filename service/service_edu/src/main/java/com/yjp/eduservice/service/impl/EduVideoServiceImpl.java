package com.yjp.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjp.eduservice.client.VodClient;
import com.yjp.eduservice.entity.EduVideo;
import com.yjp.eduservice.mapper.EduVideoMapper;
import com.yjp.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-13
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        //1.根据课程id查询出课程所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");//查询指定字段
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList){
            String videoId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoId)){
                ids.add(videoId);
            }
        }
        if (ids.size()>0){
            vodClient.deleteBatch(ids);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
