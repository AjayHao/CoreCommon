package com.ajayhao.core.framework.validator.text;


import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;

/**
 * 空值验证器
 */
public class IsBlankValidator extends AbstractValidator implements Validator {
    public IsBlankValidator() {
        super(null);
    }

    @Override
    public boolean doValidate(Object target) {
        if (target == null) {
            return true;
        }

        if (!(target instanceof CharSequence)) {
            return false;
        }

        return ((CharSequence) target).toString().trim().isEmpty();
    }
}
