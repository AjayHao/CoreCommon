package com.ajayhao.core.framework.validator.core;

import com.ajayhao.core.framework.validator.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的验证者类型<br/>
 *
 */
public class ProxyValidator implements Validator {

    public ProxyValidator() {
        ;
    }

    List<Validator> validators = new ArrayList<Validator>(5);

    @Override
    public void validate(Object target) {
        for(Validator validator : validators) {
            validator.validate(target);
        }
    }

    public void addValidate(Validator validator) {
        validators.add(validator);
    }
}
