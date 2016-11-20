/**
 * Qiangungun.com Inc.
 * <p/>
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ajayhao.core.framework.validator.collection;

import com.qiangungun.core.framework.validator.Validator;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by wu.charles on 2015/12/10.
 */
public class InValidator extends AbstractCollectionValidator implements Validator {
    public InValidator(Object referObject) {
        super(referObject);
    }

    @Override
    public boolean doValidate(Object target) {
        Collection<?> coll = (Collection<?>) getReferObject();

        return (isCollection(target)) ? coll.containsAll((Collection<?>) target) :
                ((isArray(target)) ? coll.containsAll(Arrays.asList((Object[])target)) : coll.contains(target));
    }
}
