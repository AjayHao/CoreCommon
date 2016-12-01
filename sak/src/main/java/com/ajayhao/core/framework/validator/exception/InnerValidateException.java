package com.ajayhao.core.framework.validator.exception;


import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.exception.BizException;

import java.io.Serializable;

/**
 * 表示一个内部验证器异常<br/>
 */
public class InnerValidateException extends BizException implements Serializable {
    private static final long serialVersionUID = 538058751938222844L;

    public InnerValidateException() {
        super();
    }

    public InnerValidateException(BizCode code, String message) {
        super(code, message);
    }

    public InnerValidateException(BizCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public InnerValidateException(BizCode code, Throwable cause) {
        super(code, cause);
    }
}
