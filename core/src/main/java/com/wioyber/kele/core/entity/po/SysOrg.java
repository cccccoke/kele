package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Sys org.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Data
public class SysOrg implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String label;

    /**
     * 简称
     */
    private String alias;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 所有父级id
     */
    private String treePath;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sequence;

    /**
     * 状态
     */
    private String status;

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
