package com.ajayhao.core.framework.validator.core;

import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 长度校验器
 */
public class LengthValidator  extends AbstractValidator implements Validator {
    private Validator numberValidator = null;

    public LengthValidator(int referObject, Validator numberValidator) {
        super(referObject);

        if(referObject < 0) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamNumberError, "长度不能小于0");
        }

        this.numberValidator = numberValidator;
    }

    @Override
    public boolean doValidate(Object target) {
        if (target == null
                || !((target instanceof CharSequence)
                        || (target instanceof Object[])
                        || (target.getClass().isArray()) // primitive array
                        || (target instanceof Collection)
                        || (target instanceof Map))) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamTypeError, "类型不匹配");
        }

        int len = getLength(target);

        try {
            numberValidator.validate(len);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private int getLength(Object target) {
        if(target instanceof CharSequence) {
            return ((CharSequence)target).length();
        } else if(target instanceof Object[]) {
            return ((Object[])target).length;
        } else if(target instanceof Collection) {
            return ((Collection)target).size();
        } else if((target.getClass().isArray()) /* primitive array*/) {
            return Array.getLength(target);
        } else if(target instanceof Map) {
            return ((Map)target).size();
        }

        return 0;
    }
}
