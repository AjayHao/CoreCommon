package com.ajayhao.core.framework.validator.text;

import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * like验证器
 */
public class LikeValidator extends AbstractValidator implements Validator {
    private Pattern pattern = null;

    public LikeValidator(String referObject) {
        super(referObject);

        if (referObject == null) {
            CoreCommonUtils.raiseValidateException(BizCode.ParamNotNull, "正则表达式不能为null");
        }

        this.pattern = Pattern.compile((String) (referObject));
    }

    @Override
    public boolean doValidate(Object target) {
        if (target == null || !(target instanceof CharSequence)) {
            return false;
        }

        Matcher matcher = pattern.matcher(target.toString());

        return matcher.find();
    }
}
