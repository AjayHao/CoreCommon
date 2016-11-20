package com.ajayhao.core.framework.validator.core;


import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;

/**
 * not验证器<br/>
 * <p>
 */
public class NotValidator extends AbstractValidator implements Validator {
    /**
     * 实际的验证器<br/>
     */
    private Validator innerValidator = null;

    public NotValidator(Validator innerValidator) {
        super(null);
        this.innerValidator = innerValidator;
    }

    @Override
    public boolean doValidate(Object value) {
        boolean isSuccess = true;

        try {
            innerValidator.validate(value);
        } catch (Exception e) {
            isSuccess = false;
        }
        return !(isSuccess);
    }
}
