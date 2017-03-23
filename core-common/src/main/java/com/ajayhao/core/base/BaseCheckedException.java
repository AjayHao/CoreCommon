package com.ajayhao.core.base;


import com.ajayhao.core.enums.BizCode;

import java.io.Serializable;

/**
 * 公司所有自定义检查异常的基类<br/>
 * <p>
 * Created by wu.charles on 2014/12/10.
 */
public abstract class BaseCheckedException extends Exception implements Serializable {
    private static final long serialVersionUID = 4168408977082366417L;

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

    public BaseCheckedException() {
        super();
    }

    public BaseCheckedException(BizCode code, String message) {
        super(message);

        this.code = code;
    }

    public BaseCheckedException(BizCode code, String message, BizCode rootCode, String rootMessage) {
        super(message);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
    }

    public BaseCheckedException(BizCode code, String message, Throwable cause) {
        super(message, cause);

        this.code = code;
    }

    public BaseCheckedException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause) {
        super(message, cause);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
    }

    public BaseCheckedException(BizCode code, Throwable cause) {
        super(cause);

        this.code = code;
    }

    public BaseCheckedException(BizCode code, BizCode rootCode, Throwable cause) {
        super(cause);

        this.code = code;
        this.rootCode = rootCode;
    }

    public BaseCheckedException(boolean ignoreStackTrace) {
        super(null, null, true, !ignoreStackTrace);

        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseCheckedException(BizCode code, String message, boolean ignoreStackTrace) {
        super(message, null, true, !ignoreStackTrace);

        this.code = code;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseCheckedException(BizCode code, String message, BizCode rootCode, String rootMessage, boolean ignoreStackTrace) {
        super(message, null, true, !ignoreStackTrace);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseCheckedException(BizCode code, String message, Throwable cause, boolean ignoreStackTrace) {
        super(message, cause, true, !ignoreStackTrace);

        this.code = code;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseCheckedException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause, boolean ignoreStackTrace) {
        super(message, cause, true, !ignoreStackTrace);

        this.code = code;
        this.rootCode = rootCode;
        this.rootMessage = rootMessage;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseCheckedException(BizCode code, Throwable cause, boolean ignoreStackTrace) {
        super(null, cause, true, !ignoreStackTrace);

        this.code = code;
        this.ignoreStackTrace = ignoreStackTrace;
    }

    public BaseCheckedException(BizCode code, BizCode rootCode, Throwable cause, boolean ignoreStackTrace) {
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
