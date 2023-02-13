package com.wioyber.kele.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 通用工具类
 * @author cjg
 * @since 2023/2/11
 */
public class CommonUtil {

    /**
     * 检查属性是否存在
     * 忽略0
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the boolean
     */
    public static <T> boolean checkValidIgnoreZ(T obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return StringUtils.isNotBlank(obj.toString());
        } else if (obj instanceof Number) {
            return NumberUtils.createNumber(obj.toString()) != null;
        } else if (obj instanceof Collection) {
            return !((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Map) {
            return !((Map<?, ?>) obj).isEmpty();
        }
        return true;
    }
}
