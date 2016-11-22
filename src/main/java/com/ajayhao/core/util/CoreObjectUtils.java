package com.ajayhao.core.util;


import com.ajayhao.core.base.AbstractCodedEnum;
import com.ajayhao.core.base.AbstractEnum;
import com.ajayhao.core.base.BaseException;
import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.enums.MatchType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public abstract class CoreObjectUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CoreObjectUtils.class);

    private CoreObjectUtils() {
        ; // nothing
    }

    /**
     * 调用默认构造函数创建一个新事例<br/>
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> defaultConst = clazz.getDeclaredConstructor(null);
            if(defaultConst == null) {
                CoreCommonUtils.raiseBizException(BizCode.ParamTypeError, "类型没有默认构造函数");
            }
            defaultConst.setAccessible(true);

            return defaultConst.newInstance(null);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            CoreCommonUtils.raiseBizException(BizCode.ClassOrInstanceError, "类型错误", e);
        }

        // 无法执行的代码！！
        return null;
    }

    /**
     * 将java实例转换为json对象，按field进行转换<br/>
     *
     * @param obj
     * @return
     */
    public static String object2Json(Object obj){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOG.warn("Object to Json error: ", e);
        }

        return null;
    }

    /**
     * 将java实例转换为json对象，按field进行转换<br/>
     * NOTE: 对特殊的类型进行了自定义，例如AbstractEnum<br/>
     *
     * @param obj
     * @return
     */
    public static String object2JsonExt(Object obj){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            mapper.registerModule(sm);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOG.warn("Object to Json Ext error: ", e);
        }

        return null;
    }

    /**
     * 将json字符串转换为java实例，按照field进行转换<br/>
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String json,Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return (T) null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Module m = resolveDeserializerModule(clazz);
            mapper.registerModule(m);
            Object bean = mapper.readValue(json, clazz);
            return (T)bean;
        } catch (Exception e) {
            LOG.warn("json to Object error: ", e);
        }

        return null;
    }


    /**
     * 将json字符串转换为java实例，按照field进行转换<br/>
     * NOTE: 对特殊的类型进行了自定义，例如AbstractEnum<br/>
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2ObjectExt(String json,Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return (T) null;
        }

        try {
            return json2Object(json,clazz);
        } catch (Exception e) {
            LOG.warn("json to Object Ext error: ", e);
        }

        return null;
    }

    /**
     * 获取字段的值<br/>
     *
     * @param object
     * @param field
     * @param <T>
     * @return
     */
    public static <T> T getField(Object object, Field field) {
        boolean isStatic = CoreReflectionUtils.isStatic(field);
        if(field == null || (!isStatic && object == null)) {
            return null;
        }

        try {
            CoreReflectionUtils.makeAccessible(field);
            return (T)field.get(object);
        } catch (Exception e) {
            LOG.warn("获取field值异常", e);
        }

        return null;
    }

    /**
     * 获取字段的值<br/>
     *
     * @param object
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getField(Object object, String fieldName) {
        if(object == null || StringUtils.isEmpty(fieldName)) {
            return null;
        }


        final Field field = CoreReflectionUtils.getField(object.getClass(), fieldName);
        return getField(object, field);
    }

    /**
     * 设置field值<br/>
     *
     * @param object
     * @param field
     * @param value
     */
    public static void setField(Object object, Field field, Object value) {
        boolean isStatic = CoreReflectionUtils.isStatic(field);
        if(field == null || (!isStatic && object == null)) {
            return;
        }

        try {
            CoreReflectionUtils.makeAccessible(field);
            field.set(object, value);
        } catch (Exception e) {
            LOG.warn("设置field值异常", e);
        }
    }

    /**
     * 设置field值<br/>
     *
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setField(Object object, String fieldName, Object value) {
        if(object == null || StringUtils.isEmpty(fieldName)) {
            return;
        }

        final Field field = CoreReflectionUtils.getField(object.getClass(), fieldName);
        setField(object, field, value);
    }

    /**
     * 调用对象的方法<br/>
     *
     * @param object
     * @param method
     * @param arguments
     * @param <T>
     * @return
     */
    public static <T> T invokeMethod(Object object, Method method, Object... arguments) {
        boolean isStatic = CoreReflectionUtils.isStatic(method);
        if(method == null || (!isStatic && object == null)) {
            return null;
        }

        try {
            CoreReflectionUtils.makeAccessible(method);
            return (T)method.invoke(object, arguments);
        } catch (Exception e) {
            LOG.warn("调用方法异常", e);
        }

        return null;
    }

    /**
     * 调用对象的方法<br/>
     *
     * @param object
     * @param methodName
     * @param arguments
     * @param <T>
     * @return
     */
    public static <T> T invokeMethod(Object object, String methodName, Object... arguments) {
        if(object == null || StringUtils.isEmpty(methodName)) {
            return null;
        }

        final int argLen = arguments.length;
        final boolean argAllNull = CoreArrayUtils.isAllNull(arguments); // 所有参数都为null
        
        final Set<Method> methods = CoreReflectionUtils.getAllMethods(object.getClass());
        Set<Method>  candidates = new HashSet<>(5, 1F); // 候选方法集合；
        Method m = null;
        for(Method method : methods) {
            final Class<?>[] argTypes = method.getParameterTypes();
            final boolean nameIdentical = method.getName().equals(methodName);

            if(argTypes.length != argLen || !nameIdentical) {
                continue;
            } else if(argAllNull) {
                candidates.add(method); // 先保存，需要到后面进行统一的检查；
                continue;
            }

            final MatchType type = CoreArrayUtils.compatible(arguments, argTypes);
            if(type == MatchType.Identical) { // 完全一致，则不需要继续查找；
                m = method;
                break;
            } else if(type == MatchType.Similar) {
                candidates.add(method); // 参数兼容；
            }
        }

        if(m != null) { // 精确匹配的函数
            return invokeMethod(object, m, arguments);
        }

        if(candidates.size() == 1) {
            m = candidates.iterator().next(); // 如果参数全部为null的情况，则使用该方法；
        } else if(candidates.size() > 1) {
            LOG.warn("没有找到兼容的方法：" + methodName + ", 参数个数： " + argLen
                    + "；参数无法精确定位，无法找到匹配的重载版本！！");

            return null;
        }

        if(m == null) {
            LOG.warn("没有找到兼容的方法：" + methodName + ", 参数个数： " + argLen);

            return null;
        }

        return invokeMethod(object, m, arguments);
    }

    /**
     * 获取类型的默认值，例如数值类型为0， boolean为false，reference为null等等<br/>
     *
     * @param type
     * @return
     */
    public static Object defaultValue(Class<?> type) {
        if(type == null) {
            CoreCommonUtils.raiseBizException(BizCode.ParamError, "类型不能为空");
        }

        if(type == int.class) {
            return 0;
        } else if(type == byte.class) {
            return (byte) 0;
        } else if(type == char.class) {
            return (char) 0;
        } else if(type == short.class) {
            return (short) 0;
        } else if(type == long.class) {
            return 0L;
        } else if(type == double.class) {
            return 0D;
        } else if(type == float.class) {
            return (float) 0;
        } else if(type == boolean.class) {
            return false;
        }

        return null;
    }

    /**
     * 泛型的默认类型<br/>
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T defaultGenericValue(Class<T> type) {
        return (T) defaultValue(type);
    }

    // -------------------------------------------- 辅助方法
    private static SimpleModule sm = null;


    static {
        sm = new SimpleModule("serializeModule", new Version(1, 0, 0, "snapshot",null,null));
        sm.addSerializer(AbstractEnum.class, new JsonSerializer<AbstractEnum>() {
            @Override
            public void serialize(AbstractEnum abstractEnum, JsonGenerator jsonGenerator
                    , SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                if(abstractEnum == null) {
                    jsonGenerator.writeNull();
                } else if(abstractEnum instanceof AbstractCodedEnum) {
                    jsonGenerator.writeString(((AbstractCodedEnum) abstractEnum).code());
                } else {
                    jsonGenerator.writeString(abstractEnum.name());
                }
            }
        });
    }

    private static <T> Module resolveDeserializerModule(Class<T> clazz) {
        SimpleModule de_sm = new SimpleModule("deserializeModule1", new Version(1, 0, 0, "snapshot",null,null));
        if(AbstractCodedEnum.class.isAssignableFrom(clazz)) {
            JsonDeserializer des = deserializers.get(clazz);
            if(des == null) {
                final CodedEnumJsonDeserializer deserializer =
                        new CodedEnumJsonDeserializer((Class<AbstractCodedEnum>)clazz);
                des = deserializers.putIfAbsent(clazz, deserializer);
                de_sm.addDeserializer(clazz,(des != null) ? des : (JsonDeserializer)deserializer);
            }
        } else if(AbstractEnum.class.isAssignableFrom(clazz)) {
            JsonDeserializer des = deserializers.get(clazz);
            if(des == null) {
                final EnumJsonDeserializer deserializer =
                        new EnumJsonDeserializer((Class<AbstractEnum>)clazz);
                des = deserializers.putIfAbsent(clazz, deserializer);
                de_sm.addDeserializer(clazz,(des != null) ? des : (JsonDeserializer)deserializer);
            }
        }
        return de_sm;
    }

    private static final ConcurrentMap<Class, JsonDeserializer> deserializers = new ConcurrentHashMap<>();

    private static abstract class AbstractEnumJsonDeserializer<T extends AbstractEnum> extends JsonDeserializer<T> {
        private Class<T> clazz = null;

        public AbstractEnumJsonDeserializer(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T deserialize(JsonParser jsonParser
                , DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            try {
                final Map obj = (Map) jsonParser.readValueAs(Map.class);
                return doDeserialize(obj);
            } catch (Exception e) {
                final String obj = (String) jsonParser.readValueAs(String.class);

                return doDeserialize(obj);
            }
        }

        protected abstract T doDeserialize(Map map);
        protected abstract T doDeserialize(String value);

        protected Class<T> getClazz() {
            return clazz;
        }
    };

    private static class CodedEnumJsonDeserializer extends AbstractEnumJsonDeserializer<AbstractCodedEnum> {
        public CodedEnumJsonDeserializer(Class<AbstractCodedEnum> clazz) {
            super(clazz);
        }

        @Override
        protected AbstractCodedEnum doDeserialize(Map coded) {
            if(coded != null) {
                return AbstractCodedEnum.valueByCode(getClazz(), (String)coded.get("code"));
            }
            return null;
        }

        @Override
        protected AbstractCodedEnum doDeserialize(String value) {
            if(value != null) {
                AbstractCodedEnum $enum = AbstractCodedEnum.valueByCode(getClazz(), value);
                if($enum == null) {
                    return AbstractCodedEnum.valueOf(getClazz(), value);
                }
                return $enum;
            }
            return null;
        }
    }

    private static class EnumJsonDeserializer extends AbstractEnumJsonDeserializer<AbstractEnum> {
        public EnumJsonDeserializer(Class<AbstractEnum> clazz) {
            super(clazz);
        }

        @Override
        protected AbstractEnum doDeserialize(Map $enum) {
            return valueOf((String)$enum.get("name"));
        }

        @Override
        protected AbstractEnum doDeserialize(String value) {
            return valueOf(value);
        }

        private AbstractEnum valueOf(String value) {
            if(value != null) {
                return AbstractEnum.valueOf(getClazz(), value);
            }
            return null;
        }
    }
}
