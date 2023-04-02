package com.wioyber.kele.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wioyber.kele.core.dao.*;
import com.wioyber.kele.core.entity.po.*;
import com.wioyber.kele.core.entity.vo.LoginRtnVo;
import com.wioyber.kele.core.entity.vo.LoginVo;
import com.wioyber.kele.core.entity.vo.UserRoleResVo;
import com.wioyber.kele.core.service.ISysUserService;
import com.wioyber.kele.core.util.AESUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Sys user service.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysRoleResDao sysRoleResDao;

    @Resource
    private SysResourceDao sysResourceDao;


    @Override
    public LoginRtnVo login(LoginVo loginVo) {
        Subject subject = SecurityUtils.getSubject();
        //前端password aes解密
        subject.login(new UsernamePasswordToken(loginVo.getUsername(), AESUtil.aesDecrypt(loginVo.getPassword())));
        SysUser sysUser = getByName(loginVo.getUsername());
        return BeanUtil.copyProperties(sysUser, LoginRtnVo.class);
    }

    @Override
    public SysUser getByName(String username) {
        return sysUserDao.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username));
    }

    @Override
    @Cacheable(value = "userInfo", key = "'cahe_'+ #id")
    public UserRoleResVo getUserInfo(Long id) {
        SysUser sysUser = sysUserDao.selectById(id);
        List<SysUserRole> sysUserRoles = sysUserRoleDao.selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, id));
        List<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysRole> sysRoles = sysRoleDao.selectList(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, roleIds));
        List<SysRoleRes> sysRoleRes = sysRoleResDao.selectList(Wrappers.<SysRoleRes>lambdaQuery()
                .in(SysRoleRes::getRoleId, roleIds));
        List<Long> resIds = sysRoleRes.stream().map(SysRoleRes::getResId).collect(Collectors.toList());
        List<SysResource> sysResources = sysResourceDao.selectList(Wrappers.<SysResource>lambdaQuery()
                .in(SysResource::getId, resIds));
        return new UserRoleResVo(sysUser, sysRoles, sysResources);
    }

}
