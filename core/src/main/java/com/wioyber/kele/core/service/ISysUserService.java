package com.wioyber.kele.core.service;

import com.wioyber.kele.core.entity.po.SysUser;
import com.wioyber.kele.core.entity.vo.LoginRtnVo;
import com.wioyber.kele.core.entity.vo.LoginVo;

/**
 * @author cjg
 * @since 2023/4/1
 */
public interface ISysUserService {

    LoginRtnVo login(LoginVo loginVo);

    SysUser getByName(String username);

}
