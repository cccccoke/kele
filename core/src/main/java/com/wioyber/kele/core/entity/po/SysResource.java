package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Sys resource.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Data
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String label;

    /**
     * 该节点的所有父节点
     */
    private String treePath;

    /**
     * 权限
     */
    private String permission;

    /**
     * 父级菜单id
     */
    private Long parentId;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 排序
     */
    private Integer sequence;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 样式
     */
    private String style;

    /**
     * 类型（1=菜单;2=按钮）
     */
    private int type;

    /**
     * 1=启用;0=禁用
     */
    private String status;

    /**
     * 内置菜单（0=否;1=是）
     */
    private String readonly;

    /**
     * 公共资源ntrue是无需分配所有人就可以访问的
     */
    private String global;

    /**
     * 0=隐藏;1=显示
     */
    private String display;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人id
     */
    private Long createdBy;

    /**
     * 创建人名称
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
     * 更新人名称
     */
    private String lastModifiedName;

    /**
     * 更新时间
     */
    private LocalDateTime lastModifiedTime;

}
