package com.xiaobubuya.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.cms.entity.CrmBanner;
import com.xiaobubuya.cms.mapper.CrmBannerMapper;
import com.xiaobubuya.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-21
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(key = "'selectBannerList'",value = "banner")
    @Override
    public List<CrmBanner> selectAllList() {
        // 根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(queryWrapper);
        return list;
    }
}
