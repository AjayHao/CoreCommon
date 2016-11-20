package com.ajayhao.core.framework.validator.base;


import com.ajayhao.core.exception.ValidateException;
import com.ajayhao.core.framework.validator.Validator;
import com.ajayhao.core.framework.validator.exception.InnerValidateException;

/**
 * 验证基类<br/>
 */
public abstract class AbstractValidator implements Validator {
    /**
     * 参考对象<br/>
     */
    private Object referObject = null;

    /**
     * 验证失败后的异常信息<br/>
     */
    private ValidateException exception = null;

    public AbstractValidator(Object referObject) {
        this.referObject = referObject;
    }

    public void validate(Object target) {
        if (!doValidate(target)) {
            if (getException() != null) {
                throw getException();
            } else {
                throw new InnerValidateException();
            }
        }
    }

    public abstract boolean doValidate(Object target);

    public Object getReferObject() {
        return referObject;
    }

    protected void setReferObject(Object referObject) {
        this.referObject = referObject;
    }

    public ValidateException getException() {
        return exception;
    }

    public void setException(ValidateException exception) {
        this.exception = exception;
    }
}
