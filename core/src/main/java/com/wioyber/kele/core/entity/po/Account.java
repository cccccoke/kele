package com.wioyber.kele.core.entity.po;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.wioyber.kele.core.util.excel.IBaseExcel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cjg
 * @since 2023/1/6
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Account extends Model<Account> implements IBaseExcel {
    private Integer id;
    private String username;
    private String password;
    private String perms;
    private String role;
}
