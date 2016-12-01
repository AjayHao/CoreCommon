package com.ajayhao.core.base;


import com.ajayhao.core.enums.BizCode;

import java.io.Serializable;

/**
 * 公司所有自定义运行时异常的基类<br/>
 */
public abstract class BaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 8855513855474247939L;

    /**
     * 异常信息错误编码<br/>
     */
    private BizCode code = BizCode.Unknown;

    /**
     * 异常信息原始错误编码<br/>
     */
    private BizCode rootCode = BizCode.Unknown;

    /**
     * 异常信息原始描述信息<br/>
     */
    private String rootMessage = null;

    /**
     * 忽略异常的栈信息<br/>
     */
    private boolean ignoreStackTrace = false;

    public BaseException() {
        super();
    }

    public BaseException(BizCode code, String message) {
        super(message);

        this.code = code;
    }

    public BaseException(BizCode code, String message, BizCode rootCode, String rootMessage) {
        super(message);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
    }

    public BaseException(BizCode code, String message, Throwable cause) {
        super(message, cause);

        this.code = code;
    }

    public BaseException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause) {
        super(message, cause);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
    }

    public BaseException(BizCode code, Throwable cause) {
        super(cause);

        this.code = code;
    }

    public BaseException(BizCode code, BizCode rootCode, Throwable cause) {
        super(cause);

        this.code = code;
        this.rootCode = rootCode;
    }

    public BaseException(boolean ignoreStackTrace) {
        super(null, null, true, !ignoreStackTrace);

        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseException(BizCode code, String message, boolean ignoreStackTrace) {
        super(message, null, true, !ignoreStackTrace);

        this.code = code;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseException(BizCode code, String message, BizCode rootCode, String rootMessage, boolean ignoreStackTrace) {
        super(message, null, true, !ignoreStackTrace);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseException(BizCode code, String message, Throwable cause, boolean ignoreStackTrace) {
        super(message, cause, true, !ignoreStackTrace);

        this.code = code;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause, boolean ignoreStackTrace) {
        super(message, cause, true, !ignoreStackTrace);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseException(BizCode code, Throwable cause, boolean ignoreStackTrace) {
        super(null, cause, true, !ignoreStackTrace);

        this.code = code;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseException(BizCode code, BizCode rootCode, Throwable cause, boolean ignoreStackTrace) {
        super(null, cause, true, !ignoreStackTrace);

        this.code = code;
        this.rootCode = rootCode;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BizCode getCode() {
        return code;
    }

    public BizCode getRootCode() {
        return rootCode == null ? code : rootCode;
    }

    public String getRootMessage() {
        return rootMessage == null ? getMessage() : rootMessage;
    }

    public boolean isIgnoreStackTrace() {
        return ignoreStackTrace;
    }

    public void setIgnoreStackTrace(boolean ignoreStackTrace) {
        this.ignoreStackTrace = ignoreStackTrace;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        if (ignoreStackTrace) {
            return this;
        } else {
            return super.fillInStackTrace();
        }
    }
}
