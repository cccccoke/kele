package com.wioyber.kele.core.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cjg
 * @since 2023/4/1
 */
@Data
public class LoginRtnVo {

    private Long id;
    /**
     * 账号
     */
    private String username;

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

}
