package com.ajayhao.core.util;

import com.ajayhao.core.base.*;
import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.exception.BizException;
import com.ajayhao.core.exception.ValidateException;
import com.ajayhao.core.framework.validator.ValidatorBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 通用辅助函数<br/>
 */
public abstract class CoreCommonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CoreCommonUtils.class);

    private CoreCommonUtils() {
        ; // nothing.
    }

    /**
     * 判断编码是否为成功<br/>
     *
     * @param code
     * @return
     */
    public static boolean isSuccess(BizCode code) {
        return code != null && code == BizCode.Success;
    }

    /**
     * 在异常的情况下填充返回值<br/>
     *
     * @param response
     * @param ex
     */
    public static void fillWhenException(AbstractResponse response, Exception ex) {
        if(ex == null) { // support null.
            return;
        }

        if(ex instanceof BaseException) {
            BaseException $ex = (BaseException) ex;
            response.setCode($ex.getCode());
            response.setRootCode($ex.getRootCode());
            response.setRootMessage($ex.getRootMessage());
        } else if(ex instanceof BaseCheckedException) {
            BaseCheckedException $ex = (BaseCheckedException) ex;
            response.setCode($ex.getCode());
            response.setRootCode($ex.getRootCode());
            response.setRootMessage($ex.getRootMessage());
        } else { // 其他不明确的异常情况
            response.setCode(BizCode.Unknown);
        }

        response.setMessage(ex.getMessage());
    }

    /**
     * 设置返回值成功编码<br/>
     *
     * @param response
     */
    public static void fillSuccess(AbstractResponse response) {
        if(response == null) {
            return;
        }

        response.setCode(BizCode.Success);
        response.setMessage(BizCode.Success.desc());
    }

    /**
     * 触发运行时业务异常，抛出的异常类型BizException<br/>
     *
     * @param code
     */
    public static void raiseBizException(BizCode code) {
        raiseBizException(code, code.desc());
    }

    /**
     * 触发运行时业务异常，抛出的异常类型BizException<br/>
     *
     * @param code
     */
    public static void raiseBizException(BizCode code, BizCode rootCode) {
        raiseBizException(code, code.desc(), rootCode, rootCode.desc());
    }

    /**
     * 触发运行时业务异常，抛出的异常类型BizException<br/>
     *
     * @param code
     * @param message
     */
    public static void raiseBizException(BizCode code, String message) {
        raiseBizException(code, message, null, null);
    }

    /**
     * 触发运行时业务异常，抛出的异常类型BizException
     *
     * @param code
     * @param message
     */
    public static void raiseBizException(BizCode code, String message, BizCode rootCode, String rootMessage) {
        raiseBizException(code, message, rootCode, rootMessage, null);
    }

    /**
     * 触发参数验证异常，抛出的异常类型ValidateException
     *
     * @param code
     * @param message
     */
    public static void raiseValidateException(BizCode code, String message) {
        throw new ValidateException(code, message);
    }

    /**
     * 触发参数验证异常，抛出的异常类型ValidateException
     *
     * @param code
     * @param message
     */
    public static void raiseValidateException(BizCode code, String message, BizCode rootCode, String rootMessage) {
        throw new ValidateException(code, message, rootCode, rootMessage);
    }

    /**
     * 触发运行时业务异常，抛出的异常类型BizException
     *
     * @param code
     * @param message
     * @param cause
     */
    public static void raiseBizException(BizCode code, String message, Throwable cause) {
        if(code == null) {
            throw new IllegalArgumentException("抛出业务异常时code不能为null");
        }

        throw new BizException(code, message, cause);
    }

    /**
     * 触发运行时业务异常，抛出的异常类型BizException
     *
     * @param code
     * @param message
     * @param rootCode
     * @param cause
     */
    public static void raiseBizException(BizCode code, String message, BizCode rootCode, String rootMessage, Throwable cause) {
        if(code == null) {
            throw new IllegalArgumentException("抛出业务异常时code不能为null");
        }

        throw new BizException(code, message, rootCode, rootMessage, cause);
    }

    /**
     * 如果返回值错误，则抛出异常
     *
     * @param resp
     */
    public static void raiseIfError(AbstractResponse resp) {
        if(resp == null || resp.isSuccess()) {
            return;
        }

        raiseBizException(resp.getCode(), resp.getMessage(), resp.getRootCode(), resp.getRootMessage());
    }

    /**
     * 将check异常转换为运行时异常
     *
     * @param ex
     */
    public static void raiseRuntimeException(Exception ex) {
        if(ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else {
            raiseBizException(BizCode.Unknown, "非运行时异常!", ex);
        }
    }

    /**
     * 复制接口返回值的公共数据
     *
     * @param from
     * @param to
     */
    public static void copy(AbstractResponse from, AbstractResponse to) {
        if(from == null || to == null) {
            return;
        }

        to.setMessage(from.getMessage());
        to.setCode(from.getCode());
        to.setRootCode(from.getRootCode());
        to.setRootMessage(from.getRootMessage());
        to.setClientHostName(from.getClientHostName());
        to.setClientIp(from.getClientIp());
        to.setServerHostName(from.getServerHostName());
        to.setServerIp(from.getServerIp());
        to.copy(from.extFields());
    }

    /**
     * 对分页参数进行验证
     *
     * @param request
     */
    public static void validate(AbstractPageableRequest request) {
        new ValidatorBuilder().gt(0, "页码值必须大于0")
                .build().validate(request.getPageNo());

        new ValidatorBuilder().gt(0, "每页数量必须大于0")
                .build().validate(request.getItemPerPage());
    }

    
    /**
     * 判断字符串是否为数字<br>
     *
     * @param src
     * @return
     */
    public static boolean isNumeric(String src){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(src).matches();
    }


    /**
     * 比较两个BigDecimal的大小，不能为null
     *
     * @param left
     * @param right
     * @return
     */
    public static int compareTo(BigDecimal left, BigDecimal right) {
        return left.compareTo(right);
    }

    /**
     * 判断给定的值是否大于0
     *
     * @param value
     * @return
     */
    public static boolean greater0(BigDecimal value) {
        return compareTo(BigDecimal.ZERO, value) < 0;
    }

    /**
     * 判断给定的值是否大于等于0
     *
     * @param value
     * @return
     */
    public static boolean greaterOrEqual0(BigDecimal value) {
        return compareTo(BigDecimal.ZERO, value) <= 0;
    }

    /**
     * 判断给定的值是否等于0
     *
     * @param value
     * @return
     */
    public static boolean is0(BigDecimal value) {
        return value != null && BigDecimal.ZERO.equals(value);
    }

    /**
     * 判断给定的值是否等于0或者为null
     *
     * @param value
     * @return
     */
    public static boolean isnullOr0(BigDecimal value) {
        return value == null || BigDecimal.ZERO.equals(value);
    }

    /**
     * 将null转换为0的值
     *
     * @param value
     * @return
     */
    public static BigDecimal nullTo0(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 将0转换为null的值
     *
     * @param value
     * @return
     */
    public static BigDecimal zeroToNull(BigDecimal value) {
        return value == null || is0(value) ? null : value;
    }

    /**
     * 获取当前线程上下文的UUID值
     *
     * @return
     */
    /*public static String currThreadUuid() {
        try {
            return (get_uuid == null) ? null : (String)get_uuid.invoke(null);
        } catch (Exception e) {
            ; // ignore
        }

        return null;
    }*/

    /**
     * 获取当前线程上下文的Second UUID值
     *
     * @return
     */
    /*public static String currThreadUuidb() {
        try {
            return (get_uuidb == null) ? null : (String)get_uuidb.invoke(null);
        } catch (Exception e) {
            ; // ignore
        }

        return null;
    }*/

    /**
     * 获取当前线程上下文的Application Name值
     *
     * @return
     */
    /*public static String currThreadAppName() {
        try {
            return (get_applicationName == null) ? null : (String)get_applicationName.invoke(null);
        } catch (Exception e) {
            ; // ignore
        }

        return null;
    }*/

    /**
     * 设置digist msg
     *
     * @return
     */
    /*public static void setDigistMsg(String msg) {
        try {
            if(set_digistMsg != null){
                set_digistMsg.invoke(null,msg);
            }
        } catch (Exception e) {
            ; // ignore
        }
    }*/
    /**
     * 获取最合适的class loader实例
     *
     * @param clazz
     * @return
     */
    public static ClassLoader getClassLoader(Class<?> clazz) {
        if(clazz == null) {
            return ClassLoader.getSystemClassLoader();
        }

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader != null) {
            return loader;
        }

        return clazz.getClassLoader();
    }

    /**
     * mysql数据库分页的起始索引值
     * 主要用于: limit  start, pageSize
     *
     * @param request
     * @return
     */
    public static int mysqlPageStart(AbstractPageableRequest request) {
        return (request.getPageNo() - 1) * request.getItemPerPage();
    }

    /**
     * 实例化BizCode
     *
     * @param name
     * @param code
     * @param desc
     * @return
     */
    public static BizCode newBizCode(String name, String code, String desc) {
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(name)) {
            return null;
        }

        BizCode bizCode = fetch(name, code);
        if(bizCode != null) {
            return bizCode;
        }

        synchronized (BizCode.class) {
            // double check!!
            bizCode = fetch(name, code);
            if(bizCode != null) {
                return bizCode;
            }

            return new BizCode(name, code, desc);
        }
    }

    private static BizCode fetch(String name,  String code) {
        BizCode bizCode = (BizCode)BizCode.valueByCode(BizCode.class, code);
        if(bizCode != null) {
            return bizCode;
        } else {
            bizCode = (BizCode)BizCode.valueOf(BizCode.class, name);
            return bizCode != null?bizCode:null;
        }
    }

    private enum EnumType {Normal, Code};

    private static <T extends AbstractEnum> T fetch(Class<T> clazz, String key, EnumType type) {
        T enumInstance = null;
        if(type == EnumType.Normal) {
            enumInstance = AbstractEnum.valueOf(clazz, key);
        } else if(type == EnumType.Code) {
            enumInstance = (T)AbstractCodedEnum.valueByCode(
                    (Class<? extends  AbstractCodedEnum>) clazz, key);
        }

        return enumInstance;
    }

    /**
     * 实例化普通枚举类型
     *
     * @param name
     * @param desc
     * @return
     */
    public static <T extends AbstractEnum> T newEnum(Class<T> clazz, String name, String desc) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }

        T enumInstance = fetch(clazz, name, EnumType.Normal);
        if(enumInstance != null) {
            return enumInstance;
        }

        synchronized (clazz) {
            // double check!!
            enumInstance = fetch(clazz, name, EnumType.Normal);
            if(enumInstance != null) {
                return enumInstance;
            }

            try {
                final Constructor<T> c = clazz.getDeclaredConstructor(String.class, String.class);
                c.setAccessible(true);
                return c.newInstance(name, desc);
            } catch (Exception e) {
                LOG.warn("创建normal enum异常", e);

                return null;
            }
        }
    }

    /**
     * 实例化普通枚举类型
     *
     * @param name
     * @param desc
     * @return
     */
    public static <T extends AbstractEnum> T newCodedEnum(Class<T> clazz, String name, String code, String desc) {
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(code)) {
            return null;
        }

        T enumInstance = fetch(clazz, code, EnumType.Code);
        if(enumInstance != null) {
            return enumInstance;
        }
        enumInstance = fetch(clazz, name, EnumType.Normal);
        if(enumInstance != null) {
            return enumInstance;
        }

        synchronized (clazz) {
            // double check!!
            enumInstance = fetch(clazz, code, EnumType.Code);
            if(enumInstance != null) {
                return enumInstance;
            }
            enumInstance = fetch(clazz, name, EnumType.Normal);
            if(enumInstance != null) {
                return enumInstance;
            }

            try {
                final Constructor<T> c = clazz.getDeclaredConstructor(String.class, String.class, String.class);
                c.setAccessible(true);
                return c.newInstance(name, code, desc);
            } catch (Exception e) {
                LOG.warn("创建coded enum异常", e);

                return null;
            }
        }
    }

    /**
     * 判断任一匹配
     *
     * @param src
     * @param any
     * @return
     */
    public static <T> boolean equalsAny(T src, T... any) {
        for (T value : any) {
            if ((src == null && value == null)
                    || src.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
