package com.xiaobubuya.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import com.xiaobubuya.ucenter.entity.Member;
import com.xiaobubuya.ucenter.entity.vo.LoginVo;
import com.xiaobubuya.ucenter.entity.vo.RegisterVo;
import com.xiaobubuya.ucenter.mapper.MemberMapper;
import com.xiaobubuya.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.utils.JwtUtils;
import com.xiaobubuya.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-22
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"error");
        }

        //获取会员
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("email", email));
        if(null == member) {
            throw new GuliException(20001,"error");
        }

        //校验密码
        String str = MD5.encrypt(password);
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001,"error");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    /**
     * 会员注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
//        String mobile = registerVo.getMobile();
        String email = registerVo.getEmail();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"error");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"验证码错误");
        }

        //查询数据库中是否存在相同的邮箱
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("email", email));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"error");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }

    @Override
    public Member getByOpenid(String openid) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }
}
