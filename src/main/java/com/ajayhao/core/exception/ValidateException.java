/**
 * Qiangungun.com Inc.
 * <p>
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ajayhao.core.exception;

import com.ajayhao.core.base.BaseException;
import com.ajayhao.core.enums.BizCode;

import java.io.Serializable;

/**
 * 参数验证异常<br/>
 *
 */
public class ValidateException extends BaseException implements Serializable {
    private static final long serialVersionUID = -4299652739138318666L;

    public ValidateException() {
        super();
    }

    public ValidateException(BizCode code, String message) {
        super(code, message);
    }

    public ValidateException(BizCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ValidateException(BizCode code, Throwable cause) {
        super(code, cause);
    }

    public ValidateException(boolean ignoreStackTrace) {
        super(ignoreStackTrace);
    }

    public ValidateException(BizCode code, String message, boolean ignoreStackTrace) {
        super(code, message, ignoreStackTrace);
    }

    public ValidateException(BizCode code, String message, Throwable cause, boolean ignoreStackTrace) {
        super(code, message, cause, ignoreStackTrace);
    }

    public ValidateException(BizCode code, Throwable cause, boolean ignoreStackTrace) {
        super(code, cause, ignoreStackTrace);
    }

    public ValidateException(BizCode code, String message, BizCode rootCode, String rootMessage) {
        super(code, message, rootCode, rootMessage);
    }

    public ValidateException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause) {
        super(code, message, rootCode, rootMessage, cause);
    }

    public ValidateException(BizCode code, String message, BizCode rootCode, String rootMessage, boolean ignoreStackTrace) {
        super(code, message, rootCode, rootMessage, ignoreStackTrace);
    }

    public ValidateException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause, boolean ignoreStackTrace) {
        super(code, message, rootCode, rootMessage, cause, ignoreStackTrace);
    }

    public ValidateException(BizCode code, BizCode rootCode, Throwable cause, boolean ignoreStackTrace) {
        super(code, rootCode, cause, ignoreStackTrace);
    }
}
