package com.ajayhao.core.util;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Spring辅助工具类型<br/>
 * <p>
 */
public final class CoreSpringUtils implements ApplicationContextAware {
    private ApplicationContext context = null;

    private static ApplicationContext webAppContext = null;

    private static CoreSpringUtils INSTANCE = null;

    private static final int MAX_TIME = 5;

    private CoreSpringUtils() {
        INSTANCE = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public static void autowire(Object bean) {
        getContext(INSTANCE).getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static Object getBean(String beanName) {
        return getContext(INSTANCE).getBean(beanName);
    }

    public static <T> T getBean(Class<T> clzz) {
        return getContext(INSTANCE).getBean(clzz);
    }

    public ApplicationContext getContext() {
        return webAppContext != null ? webAppContext : context;
    }

    public static ApplicationContext getContext(CoreSpringUtils $this) {
        return webAppContext != null ? webAppContext : $this.getContext();
    }

    // --------------------------------------- spring web MVC特殊使用方法；
    static {
        try {
            final Class<?> clazz = Class.forName("org.springframework.web.context.ContextLoader");
            final Method method = ClassUtils.getPublicMethod(clazz, "getCurrentWebApplicationContext");

            int i = 0;
            Object obj = null;
            while (true) {
                try {
                    obj = method.invoke(null);

                    if (i > MAX_TIME || obj != null) {
                        break;
                    }

                    if (obj == null) {
                        ++i;
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    ; // ignore
                }
            }

            webAppContext = (ApplicationContext) obj;
        } catch (Throwable e) {
            ; // ignore;
        }
    }
}
