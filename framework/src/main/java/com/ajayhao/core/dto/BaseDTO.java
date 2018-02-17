package com.ajayhao.core.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * 类BaseDto.java的实现描述：基类
 */
public abstract class BaseDTO implements Serializable, Cloneable {

    //GSON
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().
            create();
    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#clone()
     */
    @Override
    public Object clone() {
        return SerializationUtils.clone(this);
    }

    /**
     * Object to JSON String
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        return GSON.toJson(object);
    }

    /**
     * JSON String to Object
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    /**
     * JSON String to Object
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
}
