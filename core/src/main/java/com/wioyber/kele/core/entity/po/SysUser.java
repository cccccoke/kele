package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Sys user.
 *
 * @author cjg
 * @since 2023 /4/1
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 岗位id
     */
    private Long stationId;

    /**
     * 是否内置
     */
    private String readonly;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别n#sex{w:女;m:男;n:未知}
     */
    private int sex;

    /**
     * 状态 n1启用 0禁用
     */
    private String status;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    private String description;

    /**
     * 民族
     */
    private String nation;

    /**
     * 学历
     */
    private String education;

    /**
     * 生日
     */
    private LocalDateTime birthday;

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
