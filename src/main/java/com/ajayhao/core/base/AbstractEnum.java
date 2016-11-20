package com.ajayhao.core.base;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public abstract class AbstractEnum implements Serializable
{
    private static final long serialVersionUID = -6984073040914851222L;

    /**
     * 用于约束枚举的唯一性<br/>
     *
     */
    private static final Map<String, AbstractEnum> unique = new ConcurrentHashMap(32, 1F);

    /**
     * 用于保证enum class已经初始化<br/>
     *
     */
    private static final Map<Class<? extends AbstractEnum>, Boolean> initialized = new ConcurrentHashMap(32, 1F);

    /**
     * 所有枚举类型<br/>
     *
     */
    private static final Set<Class<? extends AbstractEnum>> enumTypes = new HashSet();

    /**
     * enums type与枚举列表的映射<br/>
     *
     */
    private static final Map<Class<? extends AbstractEnum>, Set<AbstractEnum>> mapping = new ConcurrentHashMap(32, 1F);

    /**
     * 枚举名称<br/>
     *
     */
    private String name = null;
    /**
     * 基本描述信息<br/>
     *
     */
    private String desc = null;

    protected AbstractEnum() {
        ;// 解决反序列化无法构造新实例的问题！！
    }

    protected AbstractEnum(String name, String desc)
    {
        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("枚举类型的名称不能为空");
        }
        String key = enumKey(getEnumType(), name);
        AbstractEnum $enum = (AbstractEnum)unique.get(key);
        if ($enum != null) {
            throw new IllegalArgumentException("枚举常量已经存在：" + key);
        }
        this.name = name;
        setDesc(desc);

        unique.put(key, this);

        unique.put(enumKey(getClass(), name), this);

        initialized.put(getClass(), Boolean.valueOf(true));
        synchronized (getEnumType()){
            enumTypes.add(getEnumType());

            Set<AbstractEnum> set = (Set)mapping.get(getEnumType());
            if (set == null){
                set = new HashSet(10, 1F);

                mapping.put(getEnumType(), set);
            }
            set.add(this);
        }
    }

    public String name(){
        return this.name;
    }

    public String desc(){
        return this.desc;
    }

    private void setDesc(String desc){
        this.desc = desc;
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other){
        return this == other;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }

    @Override
    public String toString(){
        return this.name;
    }

    /**
     * 获取枚举的类型，主要用于枚举的合并，例如继承的枚举希望与被扩展的枚举合并，这时就需要将枚举类型统一使用基类的类型<br/>
     *
     * @return
     */
    protected abstract Class<? extends AbstractEnum> getEnumType();

    /**
     * 与enum类型不一样的是，如果enum不存在，则返回null，而不是提示异常<br/>
     *
     * @param enumType
     * @param name
     * @return
     */
    public static <T extends AbstractEnum> T valueOf(Class<? extends T> enumType, String name)
    {
        initialize(enumType);

        String key = enumKey(enumType, name);

        T value = (T)unique.get(key);
        if (value != null) {
            return value;
        }

        // 如果不存在，在到基类中查找；
        Class<?> superclass = enumType.getSuperclass();
        if (AbstractEnum.class.isAssignableFrom(superclass)) {
            return (T)valueOf((Class<? extends AbstractEnum>)superclass, name);
        }
        return value;
    }

    /**
     * 为enum生成唯一的key值<br/>
     *
     * @param enumType
     * @param name
     * @return
     */
    protected static String enumKey(Class<? extends AbstractEnum> enumType, String name)
    {
        return enumType.getName() + "#" + name;
    }

    protected static void initialize(Class<? extends AbstractEnum> enumType)
    {
        Boolean init = (Boolean)initialized.get(enumType);
        if (init == null || !init) {
            try { // 避免在没有初始化class（static block）的情况下获取枚举值
                Class.forName(enumType.getName(), true, enumType.getClassLoader());
                initialized.put(enumType, true);
            }
            catch (ClassNotFoundException e) {
                initialized.put(enumType, false);
                throw new IllegalArgumentException("枚举类型不存在", e);
            }
        }

        // 同时检查super class；
        Class<?> superclass = enumType.getSuperclass();
        if (AbstractEnum.class.isAssignableFrom(superclass)) {
            initialize((Class<? extends AbstractEnum>)superclass);
        }
    }

    public static <T extends AbstractEnum> Set<T> values(Class<? extends T> clazz) {
        initialize(clazz);
        Class<? extends AbstractEnum> enumType = getEnumType(clazz);
        if(enumType == null) {
            return Collections.emptySet();
        }

        Set<AbstractEnum> set = mapping.get(enumType);

        Set<T> copy = new HashSet<>(set.size());
        for(AbstractEnum value : set) {
            copy.add((T)value);
        }
        return copy;
    }

    public static boolean contains(Class<? extends AbstractEnum> enumType) {
        initialize(enumType);

        for(Class<? extends AbstractEnum> clazz : enumTypes) {
            if(clazz.isAssignableFrom(enumType)) {
                return true;
            }
        }

        return false;
    }

    public static Class<? extends AbstractEnum> getEnumType(Class<? extends AbstractEnum> clazz) {
        initialize(clazz);

        for(Class<? extends AbstractEnum> $clazz : enumTypes) {
            if($clazz.isAssignableFrom(clazz)) {
                return $clazz;
            }
        }
        return null;
    }

    // ----------------------------------------- 序列化/反序列化支持；
    protected static interface ParseValue {
        AbstractEnum valueOf(Class<? extends AbstractEnum> enumType, String key);
    }

    // JDK/Hessian/Dubbo serialize机制；
    protected Object readResolve() throws ObjectStreamException {
        return readResolve(this.unique, name(), new ParseValue() {
            @Override
            public AbstractEnum valueOf(Class<? extends AbstractEnum> enumType, String key) {
                return AbstractEnum.this.valueOf(enumType, key);
            }
        });
    }

    protected <T extends AbstractEnum> Object readResolve(Map<String, T> unique, final String key, ParseValue parse) {
        // 先初始化，保证类型正确
        initialize(this.getClass());

        Class<? extends AbstractEnum> enumType = this.getClass();
        // 检查是否已经存在于枚举集合中
        while (true) {
            AbstractEnum enumValue = parse.valueOf(enumType, key);
            if(enumValue != null) {
                return enumValue; // 保证“==”语义正确；
            }

            Class<?> tempClass = enumType.getSuperclass();
            if(!AbstractEnum.class.isAssignableFrom(tempClass)
                    // 以为enum type是唯一标识一个enum的键值；
                    || enumType.equals(getEnumType())) {
                break;
            }
            enumType = (Class<? extends AbstractEnum>)tempClass;
        }

        // 如果类型没有，则说明序列化的对象是新增加的，则需要进行注册并返回注册后的值；
        // 因为序列化是可以并发的，必须保证唯一性，从而保证“==”语义；
        enumType = this.getClass();
        synchronized (this.getEnumType()) {
            // double check;
            AbstractEnum enumValue = parse.valueOf(enumType, key);
            if(enumValue != null) {
                return enumValue;
            }

            // 注册；
            while (true) {
                String $key = enumKey(enumType, key);
                unique.put($key, (T) this);

                Class<?> tempClass = enumType.getSuperclass();
                if(!AbstractEnum.class.isAssignableFrom(tempClass)
                        // enums type的基类就不能注册了，否则导致重复；
                        || enumType.equals(getEnumType())) {
                    break;
                }
                enumType = (Class<? extends AbstractEnum>)tempClass;
            }

            return this;
        }
    }
}
