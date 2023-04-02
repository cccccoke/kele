package com.wioyber.kele.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wioyber.kele.core.dao.SysUserRoleDao;
import com.wioyber.kele.core.entity.po.SysUserRole;
import com.wioyber.kele.core.service.ISysUserRoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cjg
 * @since 2023/4/2
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {


    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    @Cacheable(cacheNames = "list")
    public List<SysUserRole> getList() {
        return sysUserRoleDao.selectList(Wrappers.<SysUserRole>lambdaQuery());
    }
}
