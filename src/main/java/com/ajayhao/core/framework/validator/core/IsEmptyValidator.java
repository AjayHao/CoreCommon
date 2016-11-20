package com.ajayhao.core.framework.validator.core;

import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 空值验证器
 */
public class IsEmptyValidator extends AbstractValidator implements Validator {
    public IsEmptyValidator() {
        super(null);
    }

    @Override
    public boolean doValidate(Object target) {
        return target == null
                || ((target instanceof CharSequence)
                && ((CharSequence) target).length() == 0)
                || ((target.getClass().isArray())
                && (Array.getLength(target) <= 0))
                || ((target instanceof Collection)
                && ((Collection) target).isEmpty())
                || ((target instanceof Map)
                && ((Map) target).isEmpty());
    }
}
