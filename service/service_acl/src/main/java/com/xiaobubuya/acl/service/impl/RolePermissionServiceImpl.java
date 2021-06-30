package com.xiaobubuya.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.acl.entity.Role;
import com.xiaobubuya.acl.entity.RolePermission;
import com.xiaobubuya.acl.mapper.RolePermissionMapper;
import com.xiaobubuya.acl.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public List<String> selectUsernameByRoleId(String roleId) {
        List<String> usernameList = baseMapper.selectUsernameByRoleId(roleId);
        return usernameList;
    }
}
