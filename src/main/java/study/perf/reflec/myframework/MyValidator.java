package study.perf.reflec.myframework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyValidator {

    private MyValidator() {
    }

    public static void validate(Object object) {
        Class<?> clazz = object.getClass();

        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getAnnotation(RequiredField.class) != null) {
                try {
                    Method method = clazz.getMethod(getGetMethodName(declaredField.getName()));
                    Object value = method.invoke(object);
                    if (value == null) {
                        throw new RequiredFieldException(declaredField.getName());
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new ReflectionUseException(e);
                }
            }
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if(method.getAnnotation(RequiredField.class) != null) {
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

    }

    private static String getFieldName(String methodGetName) {
        return methodGetName.substring(3, 4).toLowerCase() + methodGetName.substring(4);
    }

    private static String getGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}