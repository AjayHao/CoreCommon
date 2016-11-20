package com.ajayhao.core.framework.validator.core;

import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;

/**
 * is null验证器<br/>
 */
public class IsNullValidator extends AbstractValidator implements Validator {

    public IsNullValidator() {
        super(null);
    }

    @Override
    public boolean doValidate(Object value) {
        return value == null;
    }
}
