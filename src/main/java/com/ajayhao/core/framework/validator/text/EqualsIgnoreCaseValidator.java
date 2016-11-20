package com.ajayhao.core.framework.validator.text;

import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

/**
 * 忽略大小写验证器
 */
public class EqualsIgnoreCaseValidator extends AbstractValidator implements Validator {
    public EqualsIgnoreCaseValidator(String referObject) {
        super(referObject);

        if (referObject == null) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamNotNull, "字符串不能为null");
        }
    }

    @Override
    public boolean doValidate(Object target) {
        if (target == null || !(target instanceof CharSequence)) {
            return false;
        }

        return ((CharSequence) target).toString().equalsIgnoreCase((String) getReferObject());
    }
}
