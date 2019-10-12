package develop.toolbar.utils;

import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CollectionAdvice {

    /**
     * 检查元素
     *
     * @param collection
     * @param target
     * @param function
     * @param <E>
     * @param <R>
     * @return
     */
    public static <E, R> boolean contains(@NonNull Collection<E> collection, R target, @NonNull Function<E, R> function) {
        for (E item : collection) {
            R value = function.apply(item);
            if (target == null) {
                return value == null;
            } else if (target.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得第一个匹配的元素
     *
     * @param collection
     * @param target
     * @param function
     * @param <E>
     * @param <R>
     * @return
     */
    public static <E, R> Optional<E> getFirstMatch(@NonNull Collection<E> collection, R target, @NonNull Function<E, R> function) {
        for (E item : collection) {
            R value = function.apply(item);
            if (target == null) {
                return value == null ? Optional.ofNullable(item) : Optional.empty();
            } else if (target.equals(value)) {
                return Optional.ofNullable(item);
            }
        }
        return Optional.empty();
    }

    /**
     * 获得第一个判断是true的元素
     *
     * @param collection
     * @param predicate
     * @param <E>
     * @return
     */
    public static <E> Optional<E> getFirstTrue(@NonNull Collection<E> collection, @NonNull Predicate<E> predicate) {
        for (E item : collection) {
            if (predicate.test(item)) {
                return Optional.ofNullable(item);
            }
        }
        return Optional.empty();
    }

    /**
     * 获得第一个判断是false的元素
     *
     * @param collection
     * @param predicate
     * @param <E>
     * @return
     */
    public static <E> Optional<E> getFirstFalse(@NonNull Collection<E> collection, @NonNull Predicate<E> predicate) {
        for (E item : collection) {
            if (!predicate.test(item)) {
                return Optional.ofNullable(item);
            }
        }
        return Optional.empty();
    }

    /**
     * 获得全部匹配的元素
     *
     * @param collection
     * @param target
     * @param function
     * @param <E>
     * @param <R>
     * @return
     */
    public static <E, R> List<E> getAllMatch(@NonNull Collection<E> collection, R target, @NonNull Function<E, R> function) {
        return collection.stream().filter(item -> {
            R value = function.apply(item);
            if (target == null) {
                return value == null;
            } else {
                return target.equals(value);
            }
        }).collect(Collectors.toList());
    }
}
