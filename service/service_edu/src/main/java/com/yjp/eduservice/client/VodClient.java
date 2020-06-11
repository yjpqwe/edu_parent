package com.yjp.eduservice.client;

import com.yjp.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-vod")
@Component
public interface VodClient {
    //PathVariable路径中一定要指定参数名称，否则会出错
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")//要加完全路径
    public R removeAlyVideo(@PathVariable("id") String id);

    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
