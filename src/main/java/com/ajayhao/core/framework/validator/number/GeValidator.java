package com.ajayhao.core.framework.validator.number;


import com.ajayhao.core.framework.validator.Validator;

/**
 * 大于等于验证器
 */
public class GeValidator extends AbstractNumberValidator implements Validator {
    public GeValidator(Object referObject) {
        super(referObject);
    }

    @Override
    protected Type getType() {
        return Type.GE;
    }
}
