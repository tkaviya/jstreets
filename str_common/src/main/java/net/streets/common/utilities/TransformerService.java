package net.streets.common.utilities;

import net.streets.common.structure.Pair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static net.streets.common.structure.Pair.p;
import static net.streets.common.utilities.Reflection.invoke;
import static net.streets.common.utilities.Reflection.resolveMethod;

public class TransformerService {

    static Map<Pair<Class<?>, Class<?>>, TypeTransformer<?, ?>> CONVERSION_REGISTRY;

    static {
        registerConversion(String.class, Long.class, Long::valueOf);
        registerConversion(String.class, Long.TYPE, Long::valueOf);
        registerConversion(String.class, Integer.class, Integer::valueOf);
        registerConversion(String.class, Integer.TYPE, Integer::valueOf);
        registerConversion(String.class, Byte.class, Byte::valueOf);
        registerConversion(String.class, Byte.TYPE, Byte::valueOf);
        registerConversion(String.class, Short.class, Short::valueOf);
        registerConversion(String.class, Short.TYPE, Short::valueOf);
        registerConversion(String.class, Boolean.class, StrTransformer::toBoolean);
        registerConversion(String.class, Boolean.TYPE, StrTransformer::toBoolean);
        registerConversion(String.class, Float.class, Float::valueOf);
        registerConversion(String.class, Float.TYPE, Float::valueOf);
        registerConversion(String.class, Double.class, Double::valueOf);
        registerConversion(String.class, Double.TYPE, Double::valueOf);
        registerConversion(String.class, Character.class, (instance) -> (instance != null) ? instance.charAt(0) : (char) (byte) 0);
        registerConversion(String.class, Character.TYPE, (instance) -> (instance != null) ? instance.charAt(0) : (char) (byte) 0);
        registerConversion(String.class, Double.TYPE, Double::valueOf);
        registerConversion(String.class, BigDecimal.class, BigDecimal::new);
        registerConversion(Number.class, Long.class, (instance) -> StrTransformer.toNumber(instance, Long.class));
        registerConversion(Number.class, Long.TYPE, (instance) -> StrTransformer.toNumber(instance, Long.class));
        registerConversion(Number.class, Integer.class, (instance) -> StrTransformer.toNumber(instance, Integer.class));
        registerConversion(Number.class, Integer.TYPE, (instance) -> StrTransformer.toNumber(instance, Integer.class));
        registerConversion(Number.class, Byte.class, (instance) -> StrTransformer.toNumber(instance, Byte.class));
        registerConversion(Number.class, Byte.TYPE, (instance) -> StrTransformer.toNumber(instance, Byte.class));
        registerConversion(Number.class, Short.class, (instance) -> StrTransformer.toNumber(instance, Short.class));
        registerConversion(Number.class, Short.TYPE, (instance) -> StrTransformer.toNumber(instance, Short.class));
        registerConversion(Number.class, Boolean.class, StrTransformer::toBoolean);
        registerConversion(Number.class, Boolean.TYPE, StrTransformer::toBoolean);
        registerConversion(Number.class, Float.class, (instance) -> StrTransformer.toNumber(instance, Float.class));
        registerConversion(Number.class, Float.TYPE, (instance) -> StrTransformer.toNumber(instance, Float.class));
        registerConversion(Number.class, Double.class, (instance) -> StrTransformer.toNumber(instance, Double.class));
        registerConversion(Number.class, Double.TYPE, (instance) -> StrTransformer.toNumber(instance, Double.class));
        registerConversion(Number.class, BigDecimal.class, StrTransformer::toBigDecimal);
        registerConversion(Object.class, String.class, Object::toString);
        registerConversion(Character.class, Boolean.class, StrTransformer::toBoolean);
        registerConversion(Number.class, Boolean.class, StrTransformer::toBoolean);
        registerConversion(Date.class, LocalDate.class, StrTransformer::dateToLocalDate);
        registerConversion(Date.class, LocalDateTime.class, StrTransformer::dateToLocalDateTime);
        registerConversion(LocalDate.class, Date.class, StrTransformer::localDateToDate);
        registerConversion(LocalDateTime.class, Date.class, StrTransformer::localDateTimeToDate);
        registerConversion(LocalDateTime.class, Long.class, StrTransformer::localDateTimeToLong);
        registerConversion(LocalDateTime.class, String.class, StrTransformer::localDateTimeToString);
        registerConversion(java.sql.Date.class, LocalDate.class, StrTransformer::sqlDateToLocalDate);
        registerConversion(Timestamp.class, LocalDateTime.class, StrTransformer::timestampToLocalDateTime);
        registerConversion(LocalDateTime.class, Timestamp.class, StrTransformer::localDateTimeToTimestamp);
        registerConversion(Long.class, LocalDateTime.class, StrTransformer::longTolocalDateTime);
        registerConversion(LocalDate.class, java.sql.Date.class, StrTransformer::localDateToSqlDate);
    }

    static Map<Pair<Class<?>, Class<?>>, TypeTransformer<?, ?>> getConversionRegistry() {
        if (CONVERSION_REGISTRY == null) {
            CONVERSION_REGISTRY = new HashMap<>();
        }
        return CONVERSION_REGISTRY;
    }

    public static <T, K> void registerConversion(Class<T> source, Class<K> target, TypeTransformer<T, K> transformer) {
        getConversionRegistry().put(p(source, target), transformer);
    }

    static TypeTransformer resolveTransformer(boolean isNull, Class<?> source, Class<?> target) {

        //special hack


        TypeTransformer<?, ?> typeTransformer = findWideningTransformer(source, target);
        if (typeTransformer == null) {
            return instance -> {
                if (target.isPrimitive()) {
                    try {
                        return target.getField("TYPE").get(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (isNull) {
                    return null;
                } else {
                    throw new IllegalArgumentException("No Type Transformer registered for " + source.getName() + "->" + target.getName());
                }
            };
        }
        return typeTransformer;
    }

    @SuppressWarnings("unchecked")
    private static TypeTransformer<?, ?> findWideningTransformer(Class<?> source, Class<?> target) {
        TypeTransformer<?, ?> typeTransformer = getConversionRegistry().get(p(source, target));
        if (typeTransformer != null) {
            return typeTransformer;
        }

        //do a widening search
        Pair<Class<?>, Class<?>> transformerKey = getConversionRegistry()
                .keySet()
                .stream()
                .filter(key -> {
                    boolean sourceAssignable = key.getLeft().isAssignableFrom(source);
                    boolean targetAssignable = key.getRight().isAssignableFrom(target);
                    return sourceAssignable && targetAssignable;
                })
                .findFirst().orElse(null);
        return getConversionRegistry().get(transformerKey);
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object instance, Class<T> target) {
        boolean isNull = instance == null;

        if (!isNull && (target.equals(instance.getClass()) || target.isAssignableFrom(instance.getClass()))) {
            return (T) instance;
        }

        if (instance instanceof String && Enum.class.isAssignableFrom(target)) {
            return (T) invoke(resolveMethod(Enum.class, "valueOf", Class.class, String.class), null, target, instance);
        }

        return (T) resolveTransformer(isNull, (isNull) ? null : instance.getClass(), target).transform(instance);
    }

}
