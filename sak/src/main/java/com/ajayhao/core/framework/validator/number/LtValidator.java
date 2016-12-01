package com.ajayhao.core.framework.validator.number;


import com.ajayhao.core.framework.validator.Validator;

/**
 * 大于验证器
 */
public class LtValidator extends AbstractNumberValidator implements Validator {
    public LtValidator(Object referObject) {
        super(referObject);
    }

    @Override
    protected Type getType() {
        return Type.LT;
    }
}
