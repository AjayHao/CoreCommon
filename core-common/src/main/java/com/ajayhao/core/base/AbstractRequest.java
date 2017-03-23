package com.ajayhao.core.base;

import com.ajayhao.core.util.CoreSystemUtils;

import java.io.Serializable;

/**
 * 服务调用请求参数基类<br/>
 *
 */
public abstract class AbstractRequest extends AbstractModel implements Serializable {
    private static final long serialVersionUID = -1605344118671426044L;

    /**
     * 调用方服务编码<br/>
     *
     */
    //private ServiceCode service = null;

    public AbstractRequest() {
        setClientIp(CoreSystemUtils.localIp());
        setClientHostName(CoreSystemUtils.localHostName());
    }

    /*public ServiceCode getService() {
        return service;
    }

    public void setService(ServiceCode service) {
        this.service = service;
    }*/

    @Override
    public String toString() {
        return "AbstractRequest{" +
                /*"service=" + service +*/
                "} " + super.toString();
    }
}
