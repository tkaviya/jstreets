package net.streets.common.structure;

import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Pair<T, K> implements Serializable {

    private T left;
    private K right;

    public Pair() {
    }

    public Pair(T left, K right) {
        this.left = left;
        this.right = right;
    }

    public static <T, K> Pair<T, K> p(T left, K right) {
        return new Pair<>(left, right);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> Pair<T, ?>[] filter(Pair<T, ?>... pairs) {
        List<Pair<T, ?>> pairList = filterList(pairs);
        return pairList.toArray(new Pair[pairList.size()]);
    }

    @SafeVarargs
    public static <T> List<Pair<T, ?>> filterList(Pair<T, ?>... pairs) {
        return Arrays.stream(pairs)
                .filter((pair) -> pair.getRight() != null)
                .collect(toList());
    }

    public T getLeft() {
        return left;
    }

    public K getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + '=' + right + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }

        Pair pair = (Pair) o;

        return !(left != null ? !left.equals(pair.left) : pair.left != null) && !(right != null ? !right.equals(pair.right) : pair.right != null);

    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    @SafeVarargs
    public static <T, K> Map<T, K> map(Pair<T, K>... pairs) {
        LinkedHashMap<T, K> map = new LinkedHashMap<>();
        for (Pair<T, K> pair : pairs) {
            map.put(pair.left, pair.right);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T, K> Pair<T, K>[] fromMap(Map<T, K> map) {
        Pair<T, K>[] pairs = new Pair[map.size()];
        Set<T> keys = map.keySet();
        int cnt = 0;
        for (T key : keys) {
            pairs[cnt++] = p(key, map.get(key));
        }
        return pairs;
    }
}
