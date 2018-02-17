package com.ajayhao.core.enumtype;

/**
 * Created by AjayHao on 2017/7/25.
 */
public enum BizCode{
    SUCCESS("0", "业务处理成功", "COMMON"),
    FAIL("1", "业务处理失败", "COMMON"),
    INVALID_PARAM("2", "参数不合法", "COMMON"),
    SYS_ERROR("98", "系统异常", "COMMON"),
    XML_PARSE_ERROR("981", "XML解析异常", "COMMON"),
    FILE_NOT_EXIST("982", "文件不存在", "COMMON"),
    UNKNOWN("99", "未知异常", "COMMON");

    private final String code;
    private final String message;
    private final String type;

    BizCode(String code, String message, String type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }


    public static BizCode getBizCode(String code) {
        for(BizCode bizCode : values()) {
            if(bizCode.getCode().equals(code)) {
                return bizCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
