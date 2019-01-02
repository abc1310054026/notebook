package com.ce.notebook.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javaBean对象工具类
 *
 * @author: ce
 * @create: 2018-10-28 17:17
 **/
public class ObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    /*
     * 获取对象指定属性值
     * @author ce
     * @date 18-10-30 下午8:56
     * @param [t, fieldKey]
     * @return E
    */
    public static <T, E> E getFieldValue (T t, String fieldKey) {
        E e = null;
        try {
            Field field = t.getClass().getDeclaredField(fieldKey);
            field.setAccessible(true);
            e = (E) field.get(t);
        } catch (NoSuchFieldException nsfe) {
//            打印详细信息
            logger.debug("对象中不存在[" + fieldKey + "]属性");
            nsfe.printStackTrace();
            return null;
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return e;
    }

    public static <T, E> List<E> getFieldValue (T[] objects, String fieldKey) {
        List<E> list = new ArrayList<>();
        for (T obj : objects) {
            list.add(getFieldValue(obj, fieldKey));
        }
        return list;
    }

    /*
     * 对象转为<属性名, 属性值>的Map
     * @author ce
     * @date 18-10-30 下午10:25
     * @param [object, emptyStringAllowToNULL]
     * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    public static Map<String, Object> JavaBean2Map (Object object, Boolean emptyString2NULL) {
        Map<String, Object> objMap = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        Object temp = null;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                temp = field.get(object);
//                判断是否将空字符串置为null
                if (emptyString2NULL && temp instanceof String && ((String) temp).length() == 0) temp = null;
                if (temp != null) objMap.put(field.getName(), temp);
            } catch (IllegalAccessException e) {
                logger.debug("Field[" + field.getName() + "]无权限访问");
                e.printStackTrace();
                return null;
            }
        }
        return objMap;
    }

    public static Map<String, Object> JavaBean2Map (Object object) {
        return JavaBean2Map(object, true);
    }

    /*
     * Map内键值对填充进JavaBean中
     * @author ce
     * @date 18-10-30 下午10:25
     * @param [objMap, t]
     * @return T
    */
    public static <T> T MapFillInJavaBean (Map<String, Object> objMap, T t) {

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(t.getClass(), Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Object value = null;
            Method setMethod = null;
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                if (objMap.containsKey(descriptor.getName())) {
                    value = objMap.get(descriptor.getName());
                    setMethod = descriptor.getWriteMethod();
                    setMethod.invoke(t, value);
                }
            }
        } catch (IntrospectionException e) {
            logger.debug("内省异常");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.debug("非法存取");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    /*
     * 更新新对象的中不为空的值到旧对象中
     * @author ce
     * @date 18-11-1 下午7:54
     * @param [newObject, oldObject]
     * @return T
    */
    public static <T> T updateObject (T newObject, T oldObject) {
        Map<String, Object> objMap = JavaBean2Map(newObject);
        return MapFillInJavaBean(objMap, oldObject);
    }

    /*
     * 获取T的实际类对象
     * @author ce
     * @date 18-10-26 下午8:05
     * @param []
     * @return java.lang.Class
     */
    public static <T> Class<T> getTClass(Object object) {
        if (object.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) object.getClass().getGenericSuperclass();
            if (type.getActualTypeArguments().length <= 0) {
                return null;
            }
            return (Class<T>) type.getActualTypeArguments()[0];
        }
        return null;
    }
}
