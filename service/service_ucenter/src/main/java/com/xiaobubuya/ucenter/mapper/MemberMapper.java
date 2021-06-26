package com.xiaobubuya.ucenter.mapper;

import com.xiaobubuya.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-22
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectRegisterCount(String day);
}
