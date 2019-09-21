package net.streets.common.utilities;


@FunctionalInterface
public interface TypeTransformer<T, K> {

    K transform(T instance);

}
