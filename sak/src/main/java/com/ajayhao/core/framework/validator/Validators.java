package com.ajayhao.core.framework.validator;


import com.ajayhao.core.framework.validator.base.AbstractValidator;
import com.ajayhao.core.framework.validator.collection.InValidator;
import com.ajayhao.core.framework.validator.core.*;
import com.ajayhao.core.framework.validator.number.GeValidator;
import com.ajayhao.core.framework.validator.number.GtValidator;
import com.ajayhao.core.framework.validator.number.LeValidator;
import com.ajayhao.core.framework.validator.number.LtValidator;
import com.ajayhao.core.framework.validator.text.*;

import java.util.Collection;

/**
 * 辅助类型<br/>
 */
public abstract class Validators {
    private Validators() {
        ; // nothings.
    }

    /**
     * null验证器<br/>
     *
     * @return
     */
    public static AbstractValidator isNull() {
        return new IsNullValidator();
    }

    /**
     * equal to验证器<br/>
     *
     * @param referObject
     * @return
     */
    public static AbstractValidator equalTo(Object referObject) {
        return new EqualToValidator(referObject);
    }

    /**
     * instance of验证器<br/>
     *
     * @param clazz
     * @return
     */
    public static AbstractValidator instanceOf(Class<?> clazz) {
        return new InstanceOfValidator(clazz);
    }

    /**
     * ==验证器<br/>
     *
     * @param referObject
     * @return
     */
    public static AbstractValidator sameInstance(Object referObject) {
        return new SameInstanceValidator(referObject);
    }

    /**
     * start with验证器<br/>
     *
     * @param subString
     * @return
     */
    public static AbstractValidator startWith(String subString) {
        return new StartWithValidator(subString);
    }

    /**
     * end with验证器<br/>
     *
     * @param subString
     * @return
     */
    public static AbstractValidator endWith(String subString) {
        return new EndWithValidator(subString);
    }

    /**
     * contains验证器<br/>
     *
     * @param subString
     * @return
     */
    public static AbstractValidator contains(String subString) {
        return new ContainsValidator(subString);
    }

    /**
     * 字符串： null Or ""验证器<br/>
     * Collection： null Or size() == 0 验证器<br/>
     * Map： null Or size() == 0 验证器<br/>
     * Array： null Or length() == 0 验证器<br/>
     *
     * @return
     */
    public static AbstractValidator isEmpty() {
        return new IsEmptyValidator();
    }

    /**
     * white space验证器<br/>
     *
     * @return
     */
    public static AbstractValidator isBlank() {
        return new IsBlankValidator();
    }

    /**
     * wite space验证器<br/>
     *
     * @return
     */
    public static AbstractValidator equalsIgnoreCase(String subString) {
        return new EqualsIgnoreCaseValidator(subString);
    }

    /**
     * like验证器<br/>
     *
     * @return
     */
    public static AbstractValidator like(String pattern) {
        return new LikeValidator(pattern);
    }

    /**
     * 完全匹配验证器<br/>
     *
     * @return
     */
    public static AbstractValidator matches(String pattern) {
        return new MatchesValidator(pattern);
    }

    /**
     * >=验证器<br/>
     *
     * @return
     */
    public static AbstractValidator ge(Number value) {
        return new GeValidator(value);
    }

    /**
     * >验证器<br/>
     *
     * @return
     */
    public static AbstractValidator gt(Number value) {
        return new GtValidator(value);
    }

    /**
     * ==验证器<br/>
     *
     * @return
     */
    public static AbstractValidator eq(Number value) {
        return new EqualToValidator(value);
    }

    /**
     * <验证器<br/>
     *
     * @return
     */
    public static AbstractValidator lt(Number value) {
        return new LtValidator(value);
    }

    /**
     * <=验证器<br/>
     *
     * @return
     */
    public static AbstractValidator le(Number value) {
        return new LeValidator(value);
    }

    /**
     * >=验证器(字符串)<br/>
     *
     * @return
     */
    public static AbstractValidator lenGe(Integer value) {
        return new LengthValidator(value, ge(value));
    }

    /**
     * >验证器(字符串)<br/>
     *
     * @return
     */
    public static AbstractValidator lenGt(int value) {
        return new LengthValidator(value, gt(value));
    }

    /**
     * ==验证器(字符串)<br/>
     *
     * @return
     */
    public static AbstractValidator lenEq(int value) {
        return new LengthValidator(value, eq(value));
    }

    /**
     * <验证器(字符串)<br/>
     *
     * @return
     */
    public static AbstractValidator lenLt(int value) {
        return new LengthValidator(value, lt(value));
    }

    /**
     * <=验证器(字符串)<br/>
     *
     * @return
     */
    public static AbstractValidator lenLe(int value) {
        return new LengthValidator(value, le(value));
    }

    /**
     * 判断元素是否在容器中（collection）<br/>
     *
     * @param value
     * @return
     */
    public static AbstractValidator in(Collection<?> value) {
        return new InValidator(value);
    }

    /**
     * 判断元素是否在容器中（array）<br/>
     *
     * @param value
     * @return
     */
    public static AbstractValidator in(Object[] value) {
        return new InValidator(value);
    }
}
