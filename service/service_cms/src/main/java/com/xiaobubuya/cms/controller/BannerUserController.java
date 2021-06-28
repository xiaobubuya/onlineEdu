package com.xiaobubuya.cms.controller;

import com.xiaobubuya.cms.entity.CrmBanner;
import com.xiaobubuya.cms.service.CrmBannerService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xiaobubuya
 * @Description: banner用户端接口
 * @Date Created in 2021-06-21 18:53
 * @Modified By:
 */
@RestController
@RequestMapping("/cms/banner")
@Api(tags = "网站首页Banner列表")
public class BannerUserController {
    @Autowired
    private CrmBannerService bannerService;

    // 查询所有banner
    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public Result index() {
        List<CrmBanner> list = bannerService.selectAllList();
        return Result.ok().data("bannerList", list);
    }
}
