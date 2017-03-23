/**
 * Qiangungun.com Inc.
 * <p/>
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ajayhao.core.framework.validator.collection;


import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * 容器相关的基类验证器<br/>
 */
public abstract class AbstractCollectionValidator extends AbstractValidator implements Validator {
    public AbstractCollectionValidator(Object referObject) {
        super(referObject);

        if (!isCollection(referObject) && !isArray(referObject)) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamError
                    , "参数必须是容器（实现Collection接口或者是array）");
        }

        if (isArray(referObject)) {
            setReferObject(Arrays.asList((Object[]) referObject));
        }
    }

    protected boolean isCollection(Object referObject) {
        return referObject instanceof Collection;
    }

    protected boolean isArray(Object referObject) {
        return referObject != null && referObject.getClass().isArray();
    }
}
