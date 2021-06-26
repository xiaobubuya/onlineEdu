package com.xiaobubuya.ucenter.service;

import com.xiaobubuya.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.ucenter.entity.vo.LoginVo;
import com.xiaobubuya.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-22
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Member getByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
