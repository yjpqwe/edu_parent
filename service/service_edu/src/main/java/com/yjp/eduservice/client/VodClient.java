package com.yjp.eduservice.client;

import com.yjp.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-vod")
@Component
public interface VodClient {
    //PathVariable路径中一定要指定参数名称，否则会出错
    @DeleteMapping("/eduvod/videoremoveAlyVideo/{id}")//要加完全路径
    public R removeAlyVideo(@PathVariable("id") String id);
}
