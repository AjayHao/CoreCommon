package com.ajayhao.core.util;

import com.ajayhao.core.annotation.FieldIgnore;
import com.ajayhao.core.annotation.FieldMask;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 打印对象<br/>
 *
 * @author hangdahong
 * @version $Id: QggReflectionToStringBuilder.java, v 0.1 2015年9月6日 下午2:59:08 hangdahong Exp $
 */
public class CoreReflectionToStringBuilder extends ReflectionToStringBuilder{

    private boolean useFieldNames = true;
    private String fieldNameValueSeparator = "=";
    private String contentStart = "[";
    private String nullText = "<null>";
    private boolean defaultFullDetail = true;
    private boolean fieldSeparatorAtStart = false;
    private String fieldSeparator = ",";
    private String sizeStartText = "<size=";
    private String sizeEndText = ">";

    private int prefix = 0;
    private int suffix = 0;
    private String mask = "******";
    private int type = 0;


    private static final ThreadLocal REGISTRY = new ThreadLocal();

    static Map getRegistry() {
        return (Map) REGISTRY.get();
    }

    static boolean isRegistered(Object value) {
        Map m = getRegistry();
        return m != null && m.containsKey(value);
    }


    /**
     * @param object
     *
     */
    public CoreReflectionToStringBuilder(Object object) {
        super(object);
    }
    
    
    protected boolean accept(Field field) {
        type = 0;
        boolean result = super.accept(field);
        FieldIgnore ignoreString = field.getAnnotation(FieldIgnore.class);
        if (!result || ignoreString != null) {
            return false;
        }

        FieldMask hiddenString = field.getAnnotation(FieldMask.class);
        if (hiddenString == null) {
            return true;
        } else {
            prefix = hiddenString.prelen();
            suffix = hiddenString.suflen();
            if (prefix == -1 && suffix == -1) {
                return false;
            }
            mask = hiddenString.mask();
            type = 1;
            return true;
        }
    }
    
    
    @Override
    protected void appendFieldsIn(Class clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            if (this.accept(field)) {
                try {
                    // Warning: Field.get(Object) creates wrappers objects
                    // for primitive types.
                    Object fieldValue = this.getValue(field);
                    this.append(fieldName, fieldValue, field);
                } catch (IllegalAccessException ex) {
                    // this can't happen. Would get a Security exception
                    // instead
                    // throw a runtime exception in case the impossible
                    // happens.
                    throw new InternalError(
                            "Unexpected IllegalAccessException: "
                                    + ex.getMessage());
                }
            }
        }
    }

    public ToStringBuilder append(String fieldName, Object obj, Field field) {
        append(getStringBuffer(), fieldName, obj, null, field);
        return this;
    }

    public void append(StringBuffer buffer, String fieldName, Object value,
            Boolean fullDetail, Field field) {
        appendFieldStart(buffer, fieldName);

        if (value == null) {
            appendNullText(buffer, fieldName);

        } else {
            appendInternal(buffer, fieldName, value, isFullDetail(fullDetail),
                    field);
        }

        appendFieldEnd(buffer, fieldName);
    }

    protected void appendFieldEnd(StringBuffer buffer, String fieldName) {
        appendFieldSeparator(buffer);
    }

    static void register(Object value) {
        if (value != null) {
            Map m = getRegistry();
            if (m == null) {
                m = new WeakHashMap();
                REGISTRY.set(m);
            }
            m.put(value, null);
        }
    }

    public void appendStart(StringBuffer buffer, Object object) {
        if (object != null) {
            appendClassName(buffer, object);
            appendIdentityHashCode(buffer, object);
            appendContentStart(buffer);
            if (fieldSeparatorAtStart) {
                appendFieldSeparator(buffer);
            }
        }
    }

    protected void appendFieldSeparator(StringBuffer buffer) {
        buffer.append(fieldSeparator);
    }

    protected void appendContentStart(StringBuffer buffer) {
        buffer.append(contentStart);
    }

    protected void appendClassName(StringBuffer buffer, Object object) {
        register(object);
        buffer.append(object.getClass().getName());
    }

    protected void appendIdentityHashCode(StringBuffer buffer, Object object) {
        register(object);
        buffer.append('@');
        buffer.append(Integer.toHexString(System.identityHashCode(object)));
    }

    protected void appendCyclicObject(StringBuffer buffer, String fieldName,
            Object value) {
        ObjectUtils.identityToString(buffer, value);
    }

    protected void appendDetail(StringBuffer buffer, String fieldName,
            Object value) {
        buffer.append(value);
    }

    protected void appendDetail(StringBuffer buffer, String fieldName,
            Object value, Field field) {
        if (type == 1) {
            buffer.append(mask(String.valueOf(value), prefix,suffix, mask));
        } else {
            buffer.append(value);
        }
    }

    /**
     *  掩蔽字符串
     * 规则为：前prefix位 + mask + 后suffix位
     *
     * @param src
     * @param prefix
     * @param suffix
     * @param mask
     * @return
     */
    public static String mask(String src, int prefix, int suffix, String mask) {
        String dest = null;
        String maskString = (mask == null ? "" : mask);
        if (StringUtils.isNotBlank(src)) {
            dest = StringUtils.left(src, prefix) + maskString
                    + StringUtils.right(src, suffix);
        }
        return dest;
    }

    protected void appendSummarySize(StringBuffer buffer, String fieldName,
            int size) {
        buffer.append(sizeStartText);
        buffer.append(size);
        buffer.append(sizeEndText);
    }

    protected void appendInternal(StringBuffer buffer, String fieldName,
            Object value, boolean detail, Field field) {
        if (isRegistered(value)
                && !(value instanceof Number || value instanceof Boolean || value instanceof Character)) {
            appendCyclicObject(buffer, fieldName, value);
            return;
        }

        register(value);

        try {
            if (value instanceof Collection) {
                if (detail) {
                    appendDetail(buffer, fieldName, (Collection) value);
                } else {
                    appendSummarySize(buffer, fieldName,
                            ((Collection) value).size());
                }

            } else if (value instanceof Map) {
                if (detail) {
                    appendDetail(buffer, fieldName, (Map) value);
                } else {
                    appendSummarySize(buffer, fieldName, ((Map) value).size());
                }

            } else if (value instanceof long[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (long[]) value);
                }

            } else if (value instanceof int[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (int[]) value);
                }

            } else if (value instanceof short[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (short[]) value);
                }

            } else if (value instanceof byte[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (byte[]) value);
                }

            } else if (value instanceof char[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (char[]) value);
                }

            } else if (value instanceof double[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (double[]) value);
                }

            } else if (value instanceof float[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (float[]) value);
                }

            } else if (value instanceof boolean[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (boolean[]) value);
                }

            } else if (value.getClass().isArray()) {
                if (detail) {
                    appendDetail(buffer, fieldName, (Object[]) value);
                }

            } else {
                if (detail) {
                    appendDetail(buffer, fieldName, value, field);
                }
            }
        } finally {
            unregister(value);
        }
    }

    static void unregister(Object value) {
        if (value != null) {
            Map m = getRegistry();
            if (m != null) {
                m.remove(value);
                if (m.isEmpty()) {
                    REGISTRY.set(null);
                }
            }
        }
    }

    protected boolean isFullDetail(Boolean fullDetailRequest) {
        if (fullDetailRequest == null) {
            return defaultFullDetail;
        }
        return fullDetailRequest.booleanValue();
    }

    protected void appendNullText(StringBuffer buffer, String fieldName) {
        buffer.append(nullText);
    }

    protected void appendFieldStart(StringBuffer buffer, String fieldName) {
        if (useFieldNames && fieldName != null) {
            buffer.append(fieldName);
            buffer.append(fieldNameValueSeparator);
        }
    }

    public void setUseFieldNames(boolean useFieldNames) {
        this.useFieldNames = useFieldNames;
    }

    public void setNullText(String nullText) {
        this.nullText = nullText;
    }

    public void setDefaultFullDetail(boolean defaultFullDetail) {
        this.defaultFullDetail = defaultFullDetail;
    }

}
