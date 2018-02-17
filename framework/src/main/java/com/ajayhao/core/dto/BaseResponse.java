package com.ajayhao.core.dto;

import com.ajayhao.core.enumtype.BizCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by AjayHao on 2018/2/17.
 */
@Setter
@Getter
public class BaseResponse<T> extends BaseDTO{

    private static final long serialVersionUID = -727133858919420054L;

    private T result;

    private String resultCode;

    private String resultMessage;

    public BaseResponse(){}

    public BaseResponse(BizCode bizCode){
        this.resultCode = bizCode.getCode();
        this.resultMessage = bizCode.getMessage();
    }

    public BaseResponse(BizCode bizCode, T result){
        this(bizCode);
        this.result = result;
    }
}
