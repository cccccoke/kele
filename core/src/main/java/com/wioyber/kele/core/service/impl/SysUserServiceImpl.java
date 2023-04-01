package com.wioyber.kele.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wioyber.kele.core.dao.SysUserDao;
import com.wioyber.kele.core.entity.po.SysUser;
import com.wioyber.kele.core.entity.vo.LoginRtnVo;
import com.wioyber.kele.core.entity.vo.LoginVo;
import com.wioyber.kele.core.service.ISysUserService;
import com.wioyber.kele.core.util.AESUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

}
