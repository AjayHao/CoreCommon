package com.ajayhao.core.exception;

import com.ajayhao.core.enumtype.BizCode;
import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

import java.io.Serializable;

/**
 * Created by haozhenjie on 2017/9/18.
 */
@Getter
public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 3391824968260177264L;

    private String code;

    public BaseException(){}

    public BaseException(BizCode bizCode){
        super(bizCode.getMessage());
        this.code = bizCode.getCode();
    }

    public BaseException(BizCode bizCode, Object... params){
        super(MessageFormatter.arrayFormat(bizCode.getMessage(), params).getMessage());
        this.code = bizCode.getCode();
    }

    public BaseException(BizCode bizCode, String exPattern, Object... params){
        super(MessageFormatter.arrayFormat(String.format("%s,%s" , bizCode.getMessage() ,exPattern), params).getMessage());
        this.code = bizCode.getCode();
    }

    public BaseException(BizCode bizCode, String exPattern, Throwable cause) {
        super(String.format("%s,%s" , bizCode.getMessage() ,exPattern, cause));
        this.code = bizCode.getCode();
    }

    public BaseException(BizCode bizCode, String exPattern, Throwable cause, Object... params) {
        super(MessageFormatter.arrayFormat(String.format("%s,%s" , bizCode.getMessage() ,exPattern), params).getMessage(), cause);
        this.code = bizCode.getCode();
    }
}
