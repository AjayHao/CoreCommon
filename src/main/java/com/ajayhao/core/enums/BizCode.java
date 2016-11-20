package com.ajayhao.core.enums;


import com.ajayhao.core.base.AbstractCodedEnum;
import com.ajayhao.core.base.AbstractEnum;

import java.io.Serializable;

/**
 * 业务编码，主要用于业务流程中提示操作码，例如验证异常、网络异常、数据库异常等.<br/>
 * <p/>
 * <pre>
 *     如果需要增加服务特有的业务码，则可以通过继承该类实现.
 * </pre>
 * <p/>
 */
public class BizCode extends AbstractCodedEnum implements Serializable {
    private static final long serialVersionUID = -6584286222918369706L;

    public static final BizCode Success = new BizCode("Success", "000", "成功");

    public static final BizCode Unknown = new BizCode("Unknown", "999", "未知错误");

    // --------------------------- 参数验证类
    public static final BizCode ParamNotNull
            = new BizCode("ParamNotNull", "001", "参数不能为null值");

    public static final BizCode ParamIsNull
            = new BizCode("ParamIsNull", "002", "参数必须为null值");

    public static final BizCode ParamNotEqual
            = new BizCode("ParamNotEqual", "003", "参数不能与特殊值相等");

    public static final BizCode ParamIsEqual
            = new BizCode("ParamIsEqual", "004", "参数必须与特殊值相等");

    public static final BizCode ParamTypeError
            = new BizCode("ParamTypeError", "005", "参数类型不正确");

    public static final BizCode ParamGE0
            = new BizCode("ParamGE0", "006", "参数必须大于等于0");

    public static final BizCode ParamGT0
            = new BizCode("ParamGT0", "007", "参数必须大于0");

    public static final BizCode ParamTooLitter
            = new BizCode("ParamTooLitter", "008", "参数值太小");

    public static final BizCode ParamTooGreater
            = new BizCode("ParamTooGreater", "009", "参数值太大");

    public static final BizCode ParamNumberError
            = new BizCode("ParamNumberError", "010", "参数不合法");

    public static final BizCode ParamNotEmpty
            = new BizCode("ParamNotEmpty", "011", "参数不能为空");

    public static final BizCode ParamNotBlank
            = new BizCode("ParamNotBlank", "012", "参数不能为空白");

    public static final BizCode ParamLenError
            = new BizCode("ParamLenError", "013", "参数长度错误");

    public static final BizCode ParamIsEmpty
            = new BizCode("ParamIsEmpty", "014", "参数必须为空");

    public static final BizCode ParamIsBlank
            = new BizCode("ParamIsBlank", "015", "参数必须为空白");

    public static final BizCode ParamStringLenError
            = new BizCode("ParamStringLenError", "016", "参数长度不合法");

    public static final BizCode ParamStringError
            = new BizCode("ParamStringError", "017", "参数不合法");

    public static final BizCode ParamError
            = new BizCode("ParamError", "018", "参数不合法");

    public static final BizCode ParamCollectionError
            = new BizCode("ParamCollectionError", "030", "容器元素不合法");

    // --------------------------- 业务验证类
    public static final BizCode BizDataNotExists
            = new BizCode("BizDataNotExists", "019", "数据不存在");

    public static final BizCode ConcurrentOperation
            = new BizCode("ConcurrentOperation", "020", "并发操作");

    public static final BizCode UnsupportedOperation
            = new BizCode("UnsupportedOperation", "022", "不支持的操作");

    public static final BizCode DuplicatedError
            = new BizCode("DuplicatedError", "026", "数据重复");

    // --------------------------- 网络验证类
    public static final BizCode NetworkTimeout
            = new BizCode("NetworkTimeout", "023", "网络超时");


    // --------------------------- 平台相关类
    public static final BizCode ClassOrInstanceError
            = new BizCode("ClassOrInstanceError", "024", "clas或者新建实例相关");

    public static final BizCode IO_Error
            = new BizCode("IO_Error", "025", "IO操作异常");


    public static final BizCode LoginSuccess
            = new BizCode("LoginSuccess", "027", "登录成功");

    public static final BizCode LoginFail
            = new BizCode("LoginFail", "028", "登录失败");

    public static final BizCode LogoutStatus
            = new BizCode("logoutStatus", "029", "未登录");

    protected BizCode() {
        ; // 解决反序列化无法构造新实例的问题！！
    }

    public BizCode(String name, String code, String desc) {
        super(name, code, desc);

        validateCode(code);
    }

    private void validateCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("code不能为空");
        }

        if (code.length() != 3) {
            throw new IllegalArgumentException("code长度必须为3");
        }
    }

    @Override
    protected final Class<? extends AbstractEnum> getEnumType() {
        return BizCode.class;
    }

}
