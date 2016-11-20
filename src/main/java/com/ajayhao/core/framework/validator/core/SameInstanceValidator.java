package com.ajayhao.core.framework.validator.core;


import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;

/**
 * ==验证器<br/>
 */
public class SameInstanceValidator extends AbstractValidator implements Validator {
    public SameInstanceValidator(Object referObject) {
        super(referObject);
    }

    @Override
    public boolean doValidate(Object target) {
        return getReferObject() == target;
    }
}
