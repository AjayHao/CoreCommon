package com.ajayhao.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by AjayHao on 2018/2/17.
 */
@Setter
@Getter
public class BaseRequest extends BaseDTO{


    private static final long serialVersionUID = 8080183741366907756L;

    /**
     * 设备唯一标识
     */
    protected String          deviceId;

    /**
     * 当前设备联网IP
     */
    protected String          ip;

}
