package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Sys role res.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Data
public class SysRoleRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 菜单id
     */
    private Long resId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
