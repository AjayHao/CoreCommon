package com.ajayhao.core.framework.validator.core;


import com.ajayhao.core.framework.validator.base.AbstractValidator;

/**
 * instance of验证器<br/>
 */
public class InstanceOfValidator extends AbstractValidator {
    public InstanceOfValidator(Object referObject) {
        super(referObject);
    }

    @Override
    public boolean doValidate(Object target) {
        return ((Class<?>)getReferObject()).isInstance(target);
    }
}
