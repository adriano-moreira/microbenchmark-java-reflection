package study.perf.reflec.myframework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyValidatorWithCache {

    private MyValidatorWithCache() {
    }

    static Map<Class<?>, Method[]> cache = new HashMap<>();

    public static void validate(Object object) {
        Class<?> clazz = object.getClass();

        for (Method method : getMethods(clazz)) {
            try {
                Object value = method.invoke(object);
                if (value == null) {
                    throw new RequiredFieldException(getFieldName(method.getName()));
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new ReflectionUseException(e);
            }
        }
    }

    private static Method[] getMethods(Class<?> clazz) {
        if (cache.containsKey(clazz)) {
            return cache.get(clazz);
        }

        List<Method> methods = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getAnnotation(RequiredField.class) != null) {
                try {
                    Method method = clazz.getMethod(getGetMethodName(declaredField.getName()));
                    methods.add(method);
                } catch (NoSuchMethodException e) {
                    throw new ReflectionUseException(e);
                }
            }
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(RequiredField.class) != null) {
                methods.add(method);
            }
        }

        Method[] array = methods.toArray(new Method[0]);
        cache.put(clazz, array);
        return array;
    }

    private static String getFieldName(String methodGetName) {
        return methodGetName.substring(3, 4).toLowerCase() + methodGetName.substring(4);
    }

    private static String getGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
