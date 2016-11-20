package com.ajayhao.core.framework.validator.text;


import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

/**
 * 开始验证器
 */
public class StartWithValidator extends AbstractValidator implements Validator {
    public StartWithValidator(String referObject) {
        super(referObject);

        if (referObject == null) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamNotNull, "子字符串不能为null");
        }
    }

    @Override
    public boolean doValidate(Object target) {
        if (target == null || !(target instanceof CharSequence)) {
            return false;
        }

        return (target.toString()).startsWith((String) getReferObject());
    }
}
