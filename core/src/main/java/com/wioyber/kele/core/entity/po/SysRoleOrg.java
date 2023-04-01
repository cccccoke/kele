package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * The type Sys role org.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Data
public class SysRoleOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 组织id
     */
    private Long orgId;

}
