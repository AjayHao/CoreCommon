package com.ajayhao.core.base;


import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.util.CoreCommonUtils;
import com.ajayhao.core.util.CoreSystemUtils;

import java.io.Serializable;

/**
 * 服务调用返回值基类<br/>
 *
 */
public abstract class AbstractResponse extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 9132233374292213699L;

    /**
     * 服务调用运行结果编码<br/>
     *
     */
    private BizCode code = null;

    /**
     * 服务调用描述信息<br/>
     *
     */
    private String message = null;

    /**
     * 服务调用运行结果原始编码<br/>
     *
     */
    private BizCode rootCode = null;

    /**
     * 服务调用原始描述信息<br/>
     *
     */
    private String rootMessage = null;

    public AbstractResponse() {
        setServerIp(CoreSystemUtils.localIp());
        setServerHostName(CoreSystemUtils.localHostName());
    }

    /**
     * 获取服务调用运行结果编码<br/>
     *
     * @return
     */
    public BizCode getCode() {
        return code;
    }

    /**
     * 设置服务调用运行结果编码<br/>
     *
     * @param code
     */
    public void setCode(BizCode code) {
        this.code = code;
    }

    /**
     * 返回服务调用描述信息<br/>
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置服务调用描述信息<br/>
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public BizCode getRootCode() {
        return rootCode == null ? code : rootCode;
    }

    public void setRootCode(BizCode rootCode) {
        this.rootCode = rootCode;
    }

    public String getRootMessage() {
        return rootMessage == null ? message : rootMessage;
    }

    public void setRootMessage(String rootMessage) {
        this.rootMessage = rootMessage;
    }

    /**
     * 判断响应是否成功<br/>
     *
     * @return
     */
    public boolean isSuccess() {
        return CoreCommonUtils.isSuccess(getCode());
    }

    @Override
    public String toString() {
        return "AbstractResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", rootCode=" + rootCode +
                ", rootMessage='" + rootMessage + '\'' +
                '}';
    }
}
