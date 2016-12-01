package com.ajayhao.core.framework.validator.number;


import com.ajayhao.core.framework.validator.Validator;

/**
 * 小于等于验证器
 */
public class LeValidator extends AbstractNumberValidator implements Validator {
    public LeValidator(Object referObject) {
        super(referObject);
    }

    @Override
    protected Type getType() {
        return Type.LE;
    }
}
