package com.ajayhao.core.framework.validator.number;

import com.qiangungun.core.framework.validator.Validator;

/**
 * Created by wu.charles on 2014/12/15.
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
