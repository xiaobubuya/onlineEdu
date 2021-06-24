package com.xiaobubuya.cms.service;

import com.xiaobubuya.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-21
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllList();
}
