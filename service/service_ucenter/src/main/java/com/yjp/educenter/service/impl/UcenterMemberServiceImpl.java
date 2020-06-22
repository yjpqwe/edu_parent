package com.yjp.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjp.commonutils.JwtUtils;
import com.yjp.commonutils.MD5;
import com.yjp.educenter.entity.UcenterMember;
import com.yjp.educenter.entity.vo.RegisterVo;
import com.yjp.educenter.mapper.UcenterMemberMapper;
import com.yjp.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjp.servicebase.exceptionhandler.YjpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录的方法
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new YjpException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileMember == null){
            throw new YjpException(20001,"手机号为空");
        }

        //判断密码,在数据库中一般不存明文密码
        //把输入的密码进行加密，然后再跟数据库比较
        //密码加密的方式:MD5  MD5的特点:只能加密，不能解密
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new YjpException(20001,"密码不对");
        }

        //判断用户是否被禁用
        if(mobileMember.getIsDisabled()){
            throw new YjpException(20001,"用户被禁用");
        }

        //登录成功
        //生成token字符串，使用jwt工具
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//手机号
        String nickname = registerVo.getNickname();//昵称
        String password = registerVo.getPassword();//密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new YjpException(20001,"注册失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new YjpException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new YjpException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
