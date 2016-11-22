package com.ajayhao.core.framework.validator.number;

import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

import java.util.HashMap;
import java.util.Map;

/**

 */
public abstract class AbstractNumberValidator extends AbstractValidator implements Validator {
    protected static enum Type {
        GT/*大于*/, GE/*大于等于*/, EQ/*等于*/, LT/*小于*/, LE/*小于等于*/
    }

    public AbstractNumberValidator(Object referObject) {
        super(referObject);
    }

    protected abstract Type getType();

    @Override
    public boolean doValidate(Object target) {
        if (target == null) {
            return false;
        }

        Object referObject = getReferObject();

        if (!isValidateTypes(target.getClass(), referObject.getClass())) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamTypeError, "验证类型不一致");
        }

        if (!(target instanceof Comparable)) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamTypeError, "类型不支持比较操作");
        }

        Comparable left = (Comparable) target;
        int value = left.compareTo(referObject);
        switch (getType()) {
            case EQ:
                return value == 0;
            case GE:
                return value >= 0;
            case GT:
                return value > 0;
            case LE:
                return value <= 0;
            case LT:
                return value < 0;
        }

        return false;
    }

    private static boolean isValidateTypes(Class<?> left, Class<?> right) {
        if (left.isPrimitive()) {
            left = mapping.get(left);
        }

        if (right.isPrimitive()) {
            right = mapping.get(right);
        }

        return left.equals(right);
    }

    /**
     * primitive type映射表
     */
    private static final Map<Class<?>, Class<?>> mapping = new HashMap<>(8, 1F);

    static {
        mapping.put(byte.class, Byte.class);
        mapping.put(char.class, Character.class);
        mapping.put(short.class, Short.class);
        mapping.put(int.class, Integer.class);
        mapping.put(long.class, Long.class);
        mapping.put(float.class, Float.class);
        mapping.put(double.class, Double.class);
        mapping.put(boolean.class, Boolean.class);
    }
}
