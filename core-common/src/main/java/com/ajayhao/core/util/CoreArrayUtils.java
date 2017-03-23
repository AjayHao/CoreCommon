package com.ajayhao.core.util;

import com.ajayhao.core.enums.MatchType;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组相关的工具类型<br/>
 *
 */
public abstract class CoreArrayUtils {

    private CoreArrayUtils() {
        ; // nothing
    }

    /**
     * 数组中的元素是否是该component type的默认值<br/>
     *
     * @param array
     * @return
     */
    public static boolean isAllDefault(Object[] array) {
        if(ArrayUtils.isEmpty(array)) {
            return false; // 空数组无法判断，默认返回不是默认值！！
        }

        final Class<?> eleType = array.getClass().getComponentType();
        Object defValue = CoreObjectUtils.defaultValue(eleType);
        for(Object ele : array) {
            if(!((ele == null && defValue == null) || ele.equals(defValue))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断所有的参数都为null值<br/>
     *
     * @param array
     * @return
     */
    public static boolean isAllNull(Object[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return false; // 空数组无法判断，默认返回不是默认值！！
        }

        for(Object value : array) {
            if(value != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * 数组元素与类型列表是兼容的，即：数组的元素可以一对一赋值给类型数组的元素<br/>
     * <pre>
     *     规则：
     *          如果两个数组都为null，则返回不匹配
     *          如果两个数组的长度不一致，则返回不匹配
     *          如果两个数组都为空，则返回精确匹配
     *          如果argument的所有元素不为null且类型完全一致，则返回精确匹配
     *          如果argument的所有元素全部为null，则返回相似
     *          如果argument的元素部分为null或者类型兼容，则返回相似
     * </pre>
     *
     * @param arguments
     * @param argTypes
     * @return
     */
    public static MatchType compatible(Object[] arguments, Class<?>[] argTypes) {
        if(arguments == null || argTypes == null) {
            return MatchType.NotMatch;
        }

        final int argLen = arguments.length;
        if(argLen != argTypes.length) {
            return MatchType.NotMatch;
        }

        if(argLen == 0 && argTypes.length == 0) { // 一致
            return MatchType.Identical;
        } else if(isAllNull(arguments)) { // 兼容
            return MatchType.Similar;
        }

        // 如果参数全部为null，则默认是兼容的
        boolean typeIdentical = true;  // 类型完全一致；（如果类型完全一致则能够保证参数不为null）
        for(int i = 0; i < argLen; ++i) {
            Object value = arguments[i];
            Class<?> type = argTypes[i];
            typeIdentical &= value != null && type == value.getClass();

            if(value != null && !CoreReflectionUtils.isAssignable(type, value.getClass())) {
                return MatchType.NotMatch;
            }
        }

        return typeIdentical ? MatchType.Identical : MatchType.Similar;
    }
}
