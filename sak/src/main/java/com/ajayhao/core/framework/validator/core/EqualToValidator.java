package com.ajayhao.core.framework.validator.core;


import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;

/**
 * 对象equal验证器<br/>
 *
 */
public class EqualToValidator extends AbstractValidator implements Validator {
    public EqualToValidator(Object referObject) {
        super(referObject);
    }

    @Override
    public boolean doValidate(Object target) {
        return null != target && target.equals(getReferObject());
    }
}
