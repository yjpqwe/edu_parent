package com.yjp.eduservice.controller;


import com.yjp.commonutils.R;
import com.yjp.eduservice.entity.EduVideo;
import com.yjp.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-13
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("addVideo")
    private R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //后面完善，删除小节同时要把视频删除
    @DeleteMapping("{id}")
    private R deleteVideo(@PathVariable String id){
        boolean flag = eduVideoService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("getVideo/{id}")
    private R getVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("video",eduVideo);
    }

    @PostMapping("updateVideo")
    private R updateVideo(@RequestBody EduVideo eduVideo){
        boolean flag = eduVideoService.updateById(eduVideo);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

