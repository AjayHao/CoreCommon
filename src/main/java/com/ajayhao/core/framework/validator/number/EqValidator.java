package com.ajayhao.core.framework.validator.number;


import com.ajayhao.core.framework.validator.Validator;

/**
 * 等值校验器
 */
public class EqValidator extends AbstractNumberValidator implements Validator {
    public EqValidator(Object referObject) {
        super(referObject);
    }

    @Override
    protected Type getType() {
        return Type.EQ;
    }
}
