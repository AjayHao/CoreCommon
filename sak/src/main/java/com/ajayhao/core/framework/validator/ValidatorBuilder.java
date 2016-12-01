package com.ajayhao.core.framework.validator;



import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.exception.ValidateException;
import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.framework.validator.core.NotValidator;
import com.ajayhao.core.framework.validator.core.ProxyValidator;

import java.util.Collection;

/**
 * 验证器创建者<br/>
 *
 */
public final class ValidatorBuilder {
    /**
     * 验证者对象<br/>
     *
     */
    private ProxyValidator validator = null;

    /**
     * 对一个给定的待验证对象，创建合适的验证器<br/>
     *
     */
    public ValidatorBuilder() {
        validator = new ProxyValidator();
    }

    public Validator build() {
        return validator;
    }

    // ---------------------------------- 通用

    /**
     * 如果待验证的对象为null，则正常，否则提示ParamIsNull异常<br/>
     *
     * @param tip
     */
    public ValidatorBuilder isNull(String tip) {
        AbstractValidator validator = Validators.isNull();
        validator.setException(new ValidateException(BizCode.ParamIsNull, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象不为null，则正常，否则提示ParamNotNull异常<br/>
     *
     * @param tip
     * @return
     */
    public ValidatorBuilder notNull(String tip) {
        AbstractValidator validator = Validators.isNull();
        return not(validator, BizCode.ParamNotNull, tip);
    }

    /**
     *
     * @param validator
     * @param errorCode
     * @param tip
     * @return
     */
    public ValidatorBuilder not(Validator validator, BizCode errorCode, String tip) {
        AbstractValidator notValidator = new NotValidator(validator);
        notValidator.setException(new ValidateException(errorCode, tip));

        this.validator.addValidate(notValidator);

        return this;
    }

    /**
     * 如果待验证的对象与给定的对象”equals“相等，则正常，否则提示ParamIsEqual异常<br/>
     *
     * @param referObject
     * @param tip
     * @return
     */
    public ValidatorBuilder equalTo(Object referObject, String tip) {
        AbstractValidator validator = Validators.equalTo(referObject);
        validator.setException(new ValidateException(BizCode.ParamIsEqual, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象使用“instanceof“返回true，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamTypeError}异常<br/>
     *
     * @param clazz
     * @param tip
     * @return
     */
    public ValidatorBuilder instanceOf(Class<?> clazz, String tip) {
        AbstractValidator validator = Validators.instanceOf(clazz);
        validator.setException(new ValidateException(BizCode.ParamTypeError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象使用“==”进行比较返回true，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsEqual}异常<br/>
     *
     * @param referObject
     * @param tip
     * @return
     */
    public ValidatorBuilder sameInstance(Object referObject, String tip){
        AbstractValidator validator = Validators.sameInstance(referObject);
        validator.setException(new ValidateException(BizCode.ParamIsEqual, tip));

        this.validator.addValidate(validator);

        return this;
    }

    // ---------------------------------- 字符串

    /**
     * 如果待验证的字符串以给定的字符串开头，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringError}异常<br/>
     *
     * @param subString
     * @param tip
     * @return
     */
    public ValidatorBuilder startWith(String subString, String tip) {
        AbstractValidator validator = Validators.startWith(subString);
        validator.setException(new ValidateException(BizCode.ParamStringError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串为给定字符串结尾，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringError}异常<br/>
     *
     * @param subString
     * @param tip
     * @return
     */
    public ValidatorBuilder endWith(String subString, String tip) {
        AbstractValidator validator = Validators.endWith(subString);
        validator.setException(new ValidateException(BizCode.ParamStringError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串含有给定的子字符串，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringError}异常<br/>
     *
     * @param subString
     * @param tip
     * @return
     */
    public ValidatorBuilder contains(String subString, String tip) {
        AbstractValidator validator = Validators.contains(subString);
        validator.setException(new ValidateException(BizCode.ParamStringError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串为null或者是""字符串，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsEmpty}异常<br/>
     * 如果待验证的Array为null或者是length == 0，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsEmpty}异常<br/>
     * 如果待验证的Collection为null或者是size() == 0，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsEmpty}异常<br/>
     * 如果待验证的Map为null或者是size() == 0，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsEmpty}异常<br/>
     *
     * @param tip
     * @return
     */
    public ValidatorBuilder isEmpty(String tip) {
        AbstractValidator validator = Validators.isEmpty();
        validator.setException(new ValidateException(BizCode.ParamIsEmpty, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串不为null且不是""字符串，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamNotEmpty}异常<br/>
     *
     * @param tip
     * @return
     */
    public ValidatorBuilder notEmpty(String tip) {
        AbstractValidator validator = Validators.isEmpty();
        return not(validator, BizCode.ParamNotEmpty, tip);
    }

    /**
     * 如果待验证的字符串为null或者全部是空白字符，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsBlank}异常<br/>
     *
     * @param tip
     * @return
     */
    public ValidatorBuilder isBlank(String tip) {
        AbstractValidator validator = Validators.isBlank();
        validator.setException(new ValidateException(BizCode.ParamIsBlank, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串不为null且不全是空白，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamNotBlank}异常<br/>
     *
     * @param tip
     * @return
     */
    public ValidatorBuilder notBlank(String tip) {
        AbstractValidator validator = Validators.isBlank();
        return not(validator, BizCode.ParamNotBlank, tip);
    }

    /**
     * 如果待验证的字符串与给定的字符串大小写无关相同，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamIsEqual}异常<br/>
     *
     * @param string
     * @param tip
     * @return
     */
    public ValidatorBuilder equalsIgnoreCase(String string, String tip) {
        AbstractValidator validator = Validators.equalsIgnoreCase(string);
        validator.setException(new ValidateException(BizCode.ParamIsEqual, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串与给定的正则表达式匹配，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringError}异常<br/>
     *
     * @param pattern
     * @param tip
     * @return
     */
    public ValidatorBuilder like(String pattern, String tip) {
        AbstractValidator validator = Validators.like(pattern);
        validator.setException(new ValidateException(BizCode.ParamStringError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串与给定的正则表达式完全匹配，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringError}异常<br/>
     *
     * @param pattern
     * @param tip
     * @return
     */
    public ValidatorBuilder matches(String pattern, String tip) {
        AbstractValidator validator = Validators.matches(pattern);
        validator.setException(new ValidateException(BizCode.ParamStringError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串的长度与给定的字符串长度匹配，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringLenError}异常<br/>
     *
     * @param length
     * @param tip
     * @return
     */
    public ValidatorBuilder lenEq(int length, String tip) {
        AbstractValidator validator = Validators.lenEq(length);
        validator.setException(new ValidateException(BizCode.ParamStringLenError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串的长度大于给定的字符串长度，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringLenError}异常<br/>
     *
     * @param length
     * @param tip
     * @return
     */
    public ValidatorBuilder lenGt(int length, String tip) {
        AbstractValidator validator = Validators.lenGt(length);
        validator.setException(new ValidateException(BizCode.ParamStringLenError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串的长度大于等于给定的字符串长度，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringLenError}异常<br/>
     *
     * @param length
     * @param tip
     * @return
     */
    public ValidatorBuilder lenGe(int length, String tip) {
        AbstractValidator validator = Validators.lenGe(length);
        validator.setException(new ValidateException(BizCode.ParamStringLenError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串的长度小于等于给定的字符串长度，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringLenError}异常<br/>
     *
     * @param length
     * @param tip
     * @return
     */
    public ValidatorBuilder lenLe(int length, String tip) {
        AbstractValidator validator = Validators.lenLe(length);
        validator.setException(new ValidateException(BizCode.ParamStringLenError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的字符串的长度小于给定的字符串长度，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamStringLenError}异常<br/>
     *
     * @param length
     * @param tip
     * @return
     */
    public ValidatorBuilder lenLt(int length, String tip) {
        AbstractValidator validator = Validators.lenLt(length);
        validator.setException(new ValidateException(BizCode.ParamStringLenError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    // ---------------------------------- 数字

    /**
     * 如果待验证的对象大于等于（>=）给定的值，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamTooLitter}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder ge(Number value, String tip) {
        AbstractValidator validator = Validators.ge(value);
        validator.setException(new ValidateException(BizCode.ParamTooLitter, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象大于（>）给定的值，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamTooLitter}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder gt(Number value, String tip) {
        AbstractValidator validator = Validators.gt(value);
        validator.setException(new ValidateException(BizCode.ParamTooLitter, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象等于（==）给定的值，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamNotEqual}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder eq(Number value, String tip) {
        AbstractValidator validator = Validators.eq(value);
        validator.setException(new ValidateException(BizCode.ParamNotEqual, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象小于（<）给定的值，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamTooGreater}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder lt(Number value, String tip) {
        AbstractValidator validator = Validators.lt(value);
        validator.setException(new ValidateException(BizCode.ParamTooGreater, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 如果待验证的对象小于等于（<=）给定的值，则正常，否则提示{@link com.qiangungun.core.enums.BizCode#ParamTooGreater}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder le(Number value, String tip) {
        AbstractValidator validator = Validators.le(value);
        validator.setException(new ValidateException(BizCode.ParamTooGreater, tip));

        this.validator.addValidate(validator);

        return this;
    }

    // ---------------------------------- 集合

    /**
     * 判断给定的元素是否在容器中，如果不存在则提示{@link com.qiangungun.core.enums.BizCode#ParamCollectionError}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder in(Collection<?> value, String tip) {
        AbstractValidator validator = Validators.in(value);

        validator.setException(new ValidateException(BizCode.ParamCollectionError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 判断给定的元素是否在容器中，如果存在则提示{@link com.qiangungun.core.enums.BizCode#ParamCollectionError}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder notIn(Collection<?> value, String tip) {
        AbstractValidator validator = Validators.in(value);

        return not(validator, BizCode.ParamCollectionError, tip);
    }

    /**
     * 判断给定的元素是否在容器中，如果不存在则提示{@link com.qiangungun.core.enums.BizCode#ParamCollectionError}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder in(Object[] value, String tip) {
        AbstractValidator validator = Validators.in(value);

        validator.setException(new ValidateException(BizCode.ParamCollectionError, tip));

        this.validator.addValidate(validator);

        return this;
    }

    /**
     * 判断给定的元素是否在容器中，如果存在则提示{@link com.qiangungun.core.enums.BizCode#ParamCollectionError}异常<br/>
     *
     * @param value
     * @param tip
     * @return
     */
    public ValidatorBuilder notIn(Object[] value, String tip) {
        AbstractValidator validator = Validators.in(value);

        return not(validator, BizCode.ParamCollectionError, tip);
    }
}
