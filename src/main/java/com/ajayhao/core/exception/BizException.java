package com.ajayhao.core.exception;


import com.ajayhao.core.base.BaseException;
import com.ajayhao.core.enums.BizCode;

import java.io.Serializable;

/**
 * 应用业务运行时异常，只能用于服务内部，不能将此异常传递到调用服务方<br/>
 */
public class BizException extends BaseException implements Serializable {
    private static final long serialVersionUID = 4350868573071491642L;

    public BizException() {
        super();
    }

    public BizException(BizCode code, String message) {
        super(code, message);
    }

    public BizException(BizCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BizException(BizCode code, Throwable cause) {
        super(code, cause);
    }

    public BizException(boolean ignoreStackTrace) {
        super(ignoreStackTrace);
    }

    public BizException(BizCode code, String message, boolean ignoreStackTrace) {
        super(code, message, ignoreStackTrace);
    }

    public BizException(BizCode code, String message, Throwable cause, boolean ignoreStackTrace) {
        super(code, message, cause, ignoreStackTrace);
    }

    public BizException(BizCode code, Throwable cause, boolean ignoreStackTrace) {
        super(code, cause, ignoreStackTrace);
    }

    public BizException(BizCode code, BizCode rootCode, Throwable cause) {
        super(code, rootCode, cause);
    }

    public BizException(BizCode code, String message, BizCode rootCode, String rootMessage) {
        super(code, message, rootCode, rootMessage);
    }

    public BizException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause) {
        super(code, message, rootCode, rootMessage, cause);
    }

    public BizException(BizCode code, String message, BizCode rootCode, String rootMessage, boolean ignoreStackTrace) {
        super(code, message, rootCode, rootMessage, ignoreStackTrace);
    }

    public BizException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause, boolean ignoreStackTrace) {
        super(code, message, rootCode, rootMessage, cause, ignoreStackTrace);
    }

    public BizException(BizCode code, BizCode rootCode, Throwable cause, boolean ignoreStackTrace) {
        super(code, rootCode, cause, ignoreStackTrace);
    }
}
