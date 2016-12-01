/**
 * Qiangungun.com Inc.
 * <p/>
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ajayhao.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 反射相关的工具类<br/>
 *
 */
public abstract class CoreReflectionUtils {
    public static final String GET_PREFIX = "get";
    public static final String SET_PREFIX = "set";
    public static final String IS_PREFIX = "is";

    private static final Logger LOG = LoggerFactory.getLogger(CoreReflectionUtils.class);

    private CoreReflectionUtils() {
        ; // nothing
    }

    // ------------------------------------------ method

    /**
     * 在class中查找指定名称的方法定义，包括static方法（但是不会查询{@link Object}的方法）<br/>
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return 如果没有找到，则返回null
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        return getMethod(clazz, methodName, null, parameterTypes);
    }

    /**
     * 在class中查找指定名称的方法定义，只搜索static方法（但是不会查询{@link Object}的方法）<br/>
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return 如果没有找到，则返回null
     */
    public static Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        return getMethod(clazz, methodName, true, parameterTypes);
    }

    private static Method getMethod(Class<?> clazz
            , String methodName, Boolean isStatic, Class<?>... parameterTypes) {
        if (clazz == null || clazz.equals(Object.class) || StringUtils.isEmpty(methodName)) {
            return null;
        }

        Method result;
        try {
            result = clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            result = null;
        }

        if(result != null && (isStatic == null  // 不考虑static，只要有就返回;
                || (isStatic == isStatic(result)))) { // 如果考虑static属性，则必须严格匹配;
            return result;
        }

        return getMethod(clazz.getSuperclass(), methodName, isStatic, parameterTypes);
    }

    /**
     * 返回指定类型的所有方法，包括static（但是不会查询{@link Object}的方法且不包括所有类型的构造方法）<br/>
     *
     * @param clazz
     * @return
     */
    public static Set<Method> getAllMethods(Class<?> clazz) {
        return getAllMethods(clazz, null);
    }

    /**
     * 返回指定类型的所有static方法（但是不会查询{@link Object}的方法且不包括所有类型的构造方法）<br/>
     *
     * @param clazz
     * @return
     */
    public static Set<Method> getAllStaticMethods(Class<?> clazz) {
        return getAllMethods(clazz, true);
    }

    /**
     * 返回指定类型的所有static或者非static方法（但是不会查询{@link Object}的方法且不包括所有类型的构造方法）<br/>
     *
     * @param clazz
     * @param isStatic false返回非static方法，true返回static方法，null返回所有方法（包括static和非static方法）
     * @return
     */
    public static Set<Method> getAllMethods(Class<?> clazz, Boolean isStatic) {
        Set<Method> result = Collections.emptySet();
        if (clazz == null || clazz.equals(Object.class)) {
            return result;
        }

        // add all methods of this class
        Method[] declaredMethods = clazz.getDeclaredMethods();
        result = new HashSet<>(declaredMethods.length, 1F);
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isSynthetic() || declaredMethod.isBridge()
                    || (isStatic != null && isStatic != isStatic(declaredMethod))) {
                // skip methods that were added by the compiler
                continue;
            }
            result.add(declaredMethod);
        }
        // add all methods of the super-classes
        result.addAll(getAllMethods(clazz.getSuperclass(), isStatic));
        return result;
    }

    /**
     * 设置方法可访问<br/>
     *
     * @param method
     */
    public static void makeAccessible(Method method) {
        if(method == null) {
            return;
        }

        if ((!Modifier.isPublic(method.getModifiers())
                || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    // ------------------------------------------ property

    /**
     * 返回指定名称的getter方法（不区分static，但是不会查询{@link Object}）<br/>
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Method getGetter(Class<?> clazz, String propertyName) {
        return getGetter(clazz, propertyName, null);
    }

    /**
     * 返回指定名称的static getter方法（但是不会查询{@link Object}）<br/>
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Method getStaticGetter(Class<?> clazz, String propertyName) {
        return getGetter(clazz, propertyName, true);
    }

    /**
     * 返回指定类型的所有static或者非static getter（但是不会查询{@link Object}）<br/>
     *
     * @param clazz
     * @param propertyName
     * @param isStatic false返回非static getter，true返回static getter，null返回所有getter（包括static和非static geter）
     * @return
     */
    public static Method getGetter(Class<?> clazz, String propertyName, Boolean isStatic) {
        if (clazz == null || clazz.equals(Object.class) || StringUtils.isEmpty(propertyName)) {
            return null; // 不考虑object的属性；
        }

        Method result = null;
        if(isStatic == null || (isStatic != null && isStatic)) {
            result = _getStaticGetter(clazz, propertyName);
        }

        if(result != null || (isStatic !=null && isStatic)) {
            return result;
        }

        // 实例的getter方法；
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor descriptor : descriptors) {
                if(descriptor.getName().equals(propertyName)) {
                    return descriptor.getReadMethod();
                }
            }
        } catch (Exception e) {
            LOG.warn("获取getter异常", e);
        }

        return null;
    }

    private static Method _getStaticGetter(Class<?> clazz, String propertyName) {
        final Set<Method> methods = getAllStaticMethods(clazz);

        Method m = null;
        for(Method method : methods) {
            String name = method.getName();

            if (isGetter(method)
                    && ( (name.startsWith(GET_PREFIX) && propertyName.equals(Introspector.decapitalize(name.substring(3))))
                         || (name.startsWith(IS_PREFIX) && propertyName.equals(Introspector.decapitalize(name.substring(2)))))) {
                m = method;
                break;
            }
        }

        return isStatic(m) ? m : null;
    }

    /**
     * 判断给定的方法是否是一个Getter<br/>
     *
     * @param method
     * @return
     */
    public static boolean isGetter(Method method) {
        if(method == null) {
            return false;
        }

        String name = method.getName();
        Class argTypes[] = method.getParameterTypes();
        Class resultType = method.getReturnType();
        int argCount = argTypes.length;
        // 如果是“get”开头的，肯定大于3；
        if (name.length() <= 3 && !name.startsWith(IS_PREFIX)) {
            return false;
        }

        if (argCount == 0) {
            if (name.startsWith(GET_PREFIX)) {
                return true;
            } else if (resultType == boolean.class && name.startsWith(IS_PREFIX)) {
                return true;
            }
        } else if (argCount == 1) {
            if (int.class.equals(argTypes[0]) && name.startsWith(GET_PREFIX)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回指定名称的setter方法（不区分static，但是不会查询{@link Object}）<br/>
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Method getSetter(Class<?> clazz, String propertyName) {
        return getSetter(clazz, propertyName, null);
    }

    /**
     * 返回指定名称的static setter方法（但是不会查询{@link Object}）<br/>
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Method getStaticSetter(Class<?> clazz, String propertyName) {
        return getSetter(clazz, propertyName, true);
    }

    /**
     * 返回指定类型的所有static或者非static setter（但是不会查询{@link Object}）<br/>
     *
     * @param clazz
     * @param propertyName
     * @param isStatic false返回非static setter，true返回static setter，null返回所有setter（包括static和非static seter）
     * @return
     */
    public static Method getSetter(Class<?> clazz, String propertyName, Boolean isStatic) {
        if (clazz == null || clazz.equals(Object.class) || StringUtils.isEmpty(propertyName)) {
            return null; // 不考虑object的属性；
        }

        Method result = null;
        if(isStatic == null || (isStatic != null && isStatic)) {
            result = _getStaticSetter(clazz, propertyName);
        }

        if(result != null || (isStatic !=null && isStatic)) {
            return result;
        }

        // 实例的setter方法；
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor descriptor : descriptors) {
                if(descriptor.getName().equals(propertyName)) {
                    return descriptor.getWriteMethod();
                }
            }
        } catch (Exception e) {
            LOG.warn("获取setter异常", e);
        }

        return null;
    }

    private static Method _getStaticSetter(Class<?> clazz, String propertyName) {
        final Set<Method> methods = getAllStaticMethods(clazz);

        Method m = null;
        for(Method method : methods) {
            String name = method.getName();

            if (isSetter(method)
                    && propertyName.equals(Introspector.decapitalize(name.substring(3)))) {
                m = method;

                break;
            }
        }

        return isStatic(m) ? m : null;
    }

    /**
     * 判断给定的方法是否是一个Setter<br/>
     *
     * @param method
     * @return
     */
    public static boolean isSetter(Method method) {
        if(method == null) {
            return false;
        }

        String name = method.getName();
        Class argTypes[] = method.getParameterTypes();
        Class resultType = method.getReturnType();
        int argCount = argTypes.length;
        // 如果是“set”开头的，肯定大于3；
        if (name.length() <= 3) {
            return false;
        }

        if (argCount == 1) {
            if (void.class.equals(resultType) && name.startsWith(SET_PREFIX)) {
                return true;
            }
        } else if (argCount == 2) {
            if (void.class.equals(resultType) && int.class.equals(argTypes[0]) && name.startsWith(SET_PREFIX)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取方法的属性名称，如果method不是属性（getter、setter）则返回null<br/>
     *
     * @param method
     * @return
     */
    public static String propertyName(Method method) {
        if(isGetter(method)) {
            String name = method.getName();
            return name.startsWith(IS_PREFIX) ? Introspector.decapitalize(method.getName().substring(2))
                    : Introspector.decapitalize(method.getName().substring(3));
        } else if(isSetter(method)) {
            return Introspector.decapitalize(method.getName().substring(3));
        }

        return null;
    }
    // ------------------------------------------ field
    /**
     * 返回指定类型的所有字段，包括static（但是不会查询{@link Object}的字段）<br/>
     *
     * @param clazz
     * @return
     */
    public static Set<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, null);
    }

    /**
     * 返回指定类型的所有static字段（但是不会查询{@link Object}的字段）<br/>
     *
     * @param clazz
     * @return
     */
    public static Set<Field> getAllStaticFields(Class<?> clazz) {
        return getAllFields(clazz, true);
    }

    /**
     * 返回指定类型的所有static或者非static字段（但是不会查询{@link Object}的字段）<br/>
     *
     * @param clazz
     * @param isStatic false返回非static字段，true返回static字段，null返回所有字段（包括static和非static字段）
     * @return
     */
    public static Set<Field> getAllFields(Class<?> clazz, Boolean isStatic) {
        Set<Field> result = Collections.emptySet();
        if (clazz == null || clazz.equals(Object.class)) {
            return result;
        }

        // add all fields of this class
        Field[] declaredFields = clazz.getDeclaredFields();
        result = new HashSet<>(declaredFields.length, 1F);
        for(Field field : declaredFields) {
            if(isStatic != null && isStatic != isStatic(field)) {
                continue;
            }
            result.add(field);
        }

        // add all fields of the super-classes
        result.addAll(getAllFields(clazz.getSuperclass(), isStatic));
        return result;
    }

    /**
     * 返回指定类型的指定名称字段，包括static（但是不会查询{@link Object}的字段）<br/>
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        return getField(clazz, fieldName, null);
    }

    /**
     * 返回指定类型的指定名称字段，只包括static（但是不会查询{@link Object}的字段）<br/>
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getStaticField(Class<?> clazz, String fieldName) {
        return getField(clazz, fieldName, true);
    }

    /**
     * 返回指定类型的指定名称字段，static或者非static（但是不会查询{@link Object}的字段）<br/>
     *
     * @param clazz
     * @param fieldName
     * @param isStatic false返回非static字段，true返回static字段，null返回所有字段（包括static和非static字段）
     * @return
     */
    public static Field getField(Class<?> clazz, String fieldName, Boolean isStatic) {
        if (clazz == null || clazz.equals(Object.class) || StringUtils.isEmpty(fieldName)) {
            return null; // 不考虑object的属性；
        }

        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            LOG.warn("获取field失败");
            field = null;
        }

        if(field != null && (isStatic == null  // 不考虑static，只要有就返回;
                || (isStatic == isStatic(field)))) { // 如果考虑static属性，则必须严格匹配;
            return field;
        }

        return getField(clazz.getSuperclass(), fieldName, isStatic);
    }

    /**
     * 设置字段可访问<br/>
     *
     * @param field
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    // ------------------------------------------ class

    /**
     * 设置构造函数可访问<br/>
     *
     * @param ctor
     */
    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers())
                || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
                && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

    /**
     * 获取指定参数列表的构造函数<br/>
     *
     * @param clazz
     * @param parameterTypes
     * @return
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?> ...parameterTypes) {
        if(clazz == null) {
            return null;
        }

        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (Exception e) {
            LOG.warn("获取构造函数异常");
        }

        return null;
    }

    /**
     * 获取类型的所有构造函数<br/>
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Set<Constructor<T>> getConstructors(Class<T> clazz) {
        if(clazz == null) {
            return Collections.emptySet();
        }

        try {
            final Constructor<?>[] clist = clazz.getDeclaredConstructors();
            final Set<Constructor<T>> cset = new HashSet<>(clist.length, 1F);
            for(Constructor<?> c : clist) {
                cset.add((Constructor<T>)c);
            }

            return cset;
        } catch (Exception e) {
            LOG.warn("获取构造函数异常");
        }

        return Collections.emptySet();
    }

    /**
     * 判断两个类型是否兼容<br/>
     *
     * @param fromType
     * @param toType
     * @return
     */
    public static boolean isAssignable(Type fromType, Type toType) {
        if (fromType instanceof Class<?> && toType instanceof Class<?>) {
            Class<?> fromClass = (Class<?>) fromType;
            Class<?> toClass = (Class<?>) toType;

            // handle auto boxing types
            if (boolean.class.equals(fromClass) && Boolean.class.isAssignableFrom(toClass)
                    || boolean.class.equals(toClass) && Boolean.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (char.class.equals(fromClass) && Character.class.isAssignableFrom(toClass)
                    || char.class.equals(toClass) && Character.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (byte.class.equals(fromClass) && Double.class.isAssignableFrom(toClass)
                    || byte.class.equals(toClass) && Double.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (short.class.equals(fromClass) && Double.class.isAssignableFrom(toClass)
                    || short.class.equals(toClass) && Double.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (int.class.equals(fromClass) && Integer.class.isAssignableFrom(toClass)
                    || int.class.equals(toClass) && Integer.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (long.class.equals(fromClass) && Long.class.isAssignableFrom(toClass)
                    || long.class.equals(toClass) && Long.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (float.class.equals(fromClass) && Float.class.isAssignableFrom(toClass)
                    || float.class.equals(toClass) && Float.class.isAssignableFrom(fromClass)) {
                return true;
            }

            if (double.class.equals(fromClass) && Double.class.isAssignableFrom(toClass)
                    || double.class.equals(toClass) && Double.class.isAssignableFrom(fromClass)) {
                return true;
            }

            return toClass.isAssignableFrom(fromClass);
        }

        if (fromType instanceof ParameterizedType && toType instanceof ParameterizedType) {
            return isAssignable((ParameterizedType) fromType, (ParameterizedType) toType);
        }
        if (fromType instanceof WildcardType) {
            return isAssignable((WildcardType) fromType, toType);
        }
        return false;
    }

    private static boolean isAssignable(ParameterizedType lhsType, ParameterizedType rhsType) {
        if (lhsType.equals(rhsType)) {
            return true;
        }
        Type[] lhsTypeArguments = lhsType.getActualTypeArguments();
        Type[] rhsTypeArguments = rhsType.getActualTypeArguments();
        if (lhsTypeArguments.length != rhsTypeArguments.length) {
            return false;
        }
        for (int size = lhsTypeArguments.length, i = 0; i < size; ++i) {
            Type lhsArg = lhsTypeArguments[i];
            Type rhsArg = rhsTypeArguments[i];
            if (!lhsArg.equals(rhsArg) &&
                    !(lhsArg instanceof WildcardType && isAssignable((WildcardType) lhsArg, rhsArg))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAssignable(WildcardType lhsType, Type rhsType) {
        Type[] upperBounds = lhsType.getUpperBounds();
        Type[] lowerBounds = lhsType.getLowerBounds();
        for (int size = upperBounds.length, i = 0; i < size; ++i) {
            if (!isAssignable(upperBounds[i], rhsType)) {
                return false;
            }
        }
        for (int size = lowerBounds.length, i = 0; i < size; ++i) {
            if (!isAssignable(rhsType, lowerBounds[i])) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------ modifiers

    /**
     * 判断给定的访问类型是否是static的<br/>
     *
     * @param modifier
     * @return
     */
    public static boolean isStatic(int modifier) {
        return (modifier & Modifier.STATIC) != 0;
    }

    /**
     * 判断给定的方法是否是static的<br/>
     *
     * @param method
     * @return
     */
    public static boolean isStatic(Method method) {
        if(method == null) {
            return false;
        }

        return isStatic(method.getModifiers());
    }

    /**
     * 判断给定的字段是否是static的<br/>
     *
     * @param field
     * @return
     */
    public static boolean isStatic(Field field) {
        if(field == null) {
            return false;
        }

        return isStatic(field.getModifiers());
    }
}
