package com.wioyber.kele.core.entity.vo;

import com.wioyber.kele.core.entity.po.SysResource;
import com.wioyber.kele.core.entity.po.SysRole;
import com.wioyber.kele.core.entity.po.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjg
 * @since 2023/4/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResVo implements Serializable {

    private SysUser sysUser;

    private List<SysRole> sysRoleList;

    private List<SysResource> sysResourceList;

}
