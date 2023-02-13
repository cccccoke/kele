package com.wioyber.kele.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjg
 * @since 2023/2/13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GenderEnum implements BaseEnum{
    MAN(1, "男"),
    WOMEN(2, "女");
    private Integer code;
    private String msg;

   public static String getMsgByCode(Integer code) {
        Map<Integer, String> map = new HashMap<>();
        for (GenderEnum value : GenderEnum.values()) {
            map.put(value.code, value.msg);
        }
        return map.getOrDefault(code, code.toString());
    }

    public static Integer getCodeByMsg(String msg) {
        Map<String, Integer> map = new HashMap<>();
        for (GenderEnum value : GenderEnum.values()) {
            map.put(value.msg, value.code);
        }
        return map.getOrDefault(msg, null);
    }

}
