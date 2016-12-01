package com.ajayhao.core.framework.validator.text;


import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.util.CoreCommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 完全匹配验证器<br/>
 */
public class MatchesValidator extends AbstractValidator implements Validator {
    private Pattern pattern = null;

    public MatchesValidator(String referObject) {
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

        return matcher.matches();
    }
}
