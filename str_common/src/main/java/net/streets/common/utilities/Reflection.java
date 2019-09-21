package net.streets.common.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;


public class Reflection {

    public static Collection<Field> getAllFields(Class<?> type) {
        return getAllFields(type, null);
    }

    public static Optional<Field> getField(String name, Class<?> type) {
        try {
            return Optional.ofNullable(type.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            if (type.getSuperclass() != null) {
                return getField(name, type.getSuperclass());
            }
            return Optional.empty();
        }
    }

    public static Collection<Field> getAllFields(Class<?> type, Predicate<Field> fieldFilter) {

        ArrayList<Field> fields = new ArrayList<>();
        Field[] baseFields = type.getDeclaredFields();
        for (Field baseField : baseFields) {
            if (fieldFilter != null) {
                if (fieldFilter.test(baseField)) {
                    fields.add(baseField);
                }
            } else {
                fields.add(baseField);
            }
        }
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass(), fieldFilter));
        }
        return fields;

    }

    public static String getMethod(Field property) {
        Objects.requireNonNull(property);
        Inflector inflector = Inflector.getInstance();
        if (Boolean.class.isAssignableFrom(property.getType()) || boolean.class.isAssignableFrom(property.getType())) {
            return "is" + inflector.capitalise(property.getName());
        } else {
            return "get" + inflector.capitalise(property.getName());
        }

    }

    public static String setMethod(Field property) {
        Objects.requireNonNull(property);
        Inflector inflector = Inflector.getInstance();
        return "set" + inflector.capitalise(property.getName());
    }

    public static Method resolveMethod(Class<?> source, String name, Class<?>... parameterTypes) {
        try {
            return source.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Object invoke(Method method, Object target, Object... parameters) {
        try {
            return method.invoke(target, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasSetterMethod(Field property, Class<?> parent) {
        Objects.requireNonNull(property);
        String method = setMethod(property);
        Method[] methods = parent.getMethods();
        Optional<Method> setMethod = Arrays.stream(methods)
                .filter((aMethod) -> aMethod.getName().equals(method) && aMethod.getParameterCount() == 1 && aMethod.getParameterTypes()[0].isAssignableFrom(property.getType()))
                .findFirst();
        return setMethod.isPresent();
    }

    public static Field resolveField(Class<?> aClass, String name) {


        try {
            return aClass.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            if (aClass.getSuperclass() != null) {
                return resolveField(aClass.getSuperclass(), name);
            }
            return null;
        }
    }

    public static boolean setField(Class<?> type, String name, Object instance, Object value) {
        Field field = resolveField(type, name);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(instance, value);
                return true;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @SafeVarargs
    public static boolean hasAnyAnnotation(AnnotatedElement type, Class<? extends Annotation>... types) {
        for (Class<? extends Annotation> aClass : types) {
            if (type.isAnnotationPresent(aClass)) {
                return true;
            }
        }
        return false;
    }
}
