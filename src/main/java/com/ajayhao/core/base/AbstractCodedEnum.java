package com.ajayhao.core.base;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于编码的枚举类型<br/>
 *
 */
public abstract class AbstractCodedEnum extends AbstractEnum implements Serializable {
    private static final long serialVersionUID = -6350017542048337800L;

    /**
     * 用于约束枚举的唯一性<br/>
     *
     */
    private static final Map<String, AbstractCodedEnum> unique
            = new ConcurrentHashMap<String, AbstractCodedEnum>(32, 1F);

    /**
     * 编码信息<br/>
     *
     */
    private String code = null;

    protected AbstractCodedEnum() {
        ; // 解决反序列化无法构造新实例的问题！！
    }

    protected AbstractCodedEnum(String name, String code, String desc) {
        super(name, desc);

        // 不要使用StringUtils工具类型，避免对外部库的依赖
        if(code == null || code.isEmpty()) {
            throw new IllegalArgumentException("CodedEnum的编码值不能为空");
        }

        String key = enumKey(getEnumType(), code);
        AbstractEnum $enum = unique.get(key);
        if($enum != null) {
            throw new IllegalArgumentException("枚举常量已经存在： " + key);
        }

        setCode(code);

        unique.put(key, this);
        // 为何方便通过valueOf获取
        unique.put(enumKey(this.getClass(), code), this);
    }

    public String code() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public static <T extends AbstractCodedEnum> T valueByCode(Class<? extends T> enumType, String code) {
        initialize(enumType);

        String key = enumKey(enumType, code);

        T value = (T) unique.get(key);

        if(value != null) {
            return value;
        }

        // 如果不存在，在到基类中查找；
        Class<?> superclass = enumType.getSuperclass();
        if(AbstractCodedEnum.class.isAssignableFrom(superclass)) {
            return (T)valueByCode((Class<? extends AbstractCodedEnum>)superclass, code);
        }

        return value;
    }

    // ----------------------------------------- 序列化/反序列化支持；

    // JDK/Hessian/Dubbo serialize机制；
    protected Object readResolve() throws ObjectStreamException {
        super.readResolve();

        return readResolve(this.unique, code(), new ParseValue() {
            @Override
            public AbstractEnum valueOf(Class<? extends AbstractEnum> enumType, String key) {
                return valueByCode((Class<? extends AbstractCodedEnum>)enumType, key);
            }
        });
    }
}
