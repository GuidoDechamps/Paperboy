package be.solid.paperboy.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.UNORDERED;

public class GuavaCollectors {

    private GuavaCollectors() {
    }

    /**
     * Collect a stream of elements into an {@link ImmutableList}.
     */
    public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> immutableList() {
        return Collector.of(ImmutableList.Builder<T>::new,
                ImmutableList.Builder<T>::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableList.Builder<T>::build);
    }

    /**
     * Collect a stream of elements into an {@link ImmutableSet}.
     */
    public static <T> Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> immutableSet() {
        return Collector.of(ImmutableSet.Builder<T>::new,
                ImmutableSet.Builder<T>::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSet.Builder<T>::build, UNORDERED);
    }

    /**
     * Merge stream of Sets of <T> into an {@link ImmutableSet}.
     */
    public static <T> Collector<ImmutableSet<T>, ImmutableSet.Builder<T>, ImmutableSet<T>> mergeSets() {
        return Collector.of(ImmutableSet.Builder<T>::new,
                ImmutableSet.Builder<T>::addAll,
                (l, r) -> l.addAll(r.build()),
                ImmutableSet.Builder<T>::build, UNORDERED);
    }


    /**
     * Collect a stream of elements into an {@link ImmutableMap}.
     */
    public static <T, K, V> Collector<T, ImmutableMap.Builder<K, V>, ImmutableMap<K, V>>
    toImmutableMap(Function<? super T, ? extends K> keyMapper,
                   Function<? super T, ? extends V> valueMapper) {
        return Collector.of(ImmutableMap.Builder<K, V>::new,
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> l.putAll(r.build()),
                ImmutableMap.Builder<K, V>::build,
                Collector.Characteristics.UNORDERED);
    }

    public static <T, K, V> Collector<T, ImmutableSetMultimap.Builder<K, V>, ImmutableSetMultimap<K, V>>
    toImmutableSetMap(Function<? super T, ? extends K> keyMapper,
                      Function<? super T, ? extends V> valueMapper) {
        return Collector.of(ImmutableSetMultimap.Builder<K, V>::new,
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> l.putAll(r.build()),
                ImmutableSetMultimap.Builder<K, V>::build,
                Collector.Characteristics.UNORDERED);
    }

}
