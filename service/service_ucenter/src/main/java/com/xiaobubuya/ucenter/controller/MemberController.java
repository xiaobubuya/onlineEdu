package com.xiaobubuya.ucenter.controller;


import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import com.xiaobubuya.ucenter.entity.Member;
import com.xiaobubuya.ucenter.entity.vo.LoginVo;
import com.xiaobubuya.ucenter.entity.vo.RegisterVo;
import com.xiaobubuya.ucenter.service.MemberService;
import com.xiaobubuya.utils.JwtUtils;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public Result getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Member member = memberService.getById(memberId);
            return Result.ok().data("item", member);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }
}

