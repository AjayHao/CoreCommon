package com.ajayhao.core.framework.validator.collection;

import com.ajayhao.core.framework.validator.Validator;

import java.util.Arrays;
import java.util.Collection;

/**
 * 集合内验证器
 */
public class InValidator extends AbstractCollectionValidator implements Validator {
    public InValidator(Object referObject) {
        super(referObject);
    }

    @Override
    public boolean doValidate(Object target) {
        Collection<?> coll = (Collection<?>) getReferObject();

        return (isCollection(target)) ? coll.containsAll((Collection<?>) target) :
                ((isArray(target)) ? coll.containsAll(Arrays.asList((Object[]) target)) : coll.contains(target));
    }
}
