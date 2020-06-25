package com.yjp.educenter.service;

import com.yjp.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjp.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-21
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    //注册的方式
    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
