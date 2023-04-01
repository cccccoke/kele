package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Sys role.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 数据权限范围，值越大，权限越大
     */
    private int scopeType;

    /**
     * 0=正常1=禁用
     */
    private int locked;

    /**
     * 0=非 1=管理员
     */
    private int isSuper;

    /**
     * 是否内置角色
     */
    private int readonly;

    /**
     * 创建人id
     */
    private Long createdBy;

    /**
     * created_name
     */
    private String createdName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人id
     */
    private Long lastModifiedBy;

    /**
     * last_modified_name
     */
    private String lastModifiedName;

    /**
     * 更新时间
     */
    private LocalDateTime lastModifiedTime;

}
