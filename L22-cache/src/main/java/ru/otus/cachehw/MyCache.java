package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        listeners.forEach(listener -> listener.notify(key, value, "put"));

        cache.put(key, value);

    }

    @Override
    public void remove(K key) {
        V removedValue = cache.remove(key);

        listeners.forEach(listener -> listener.notify(key, removedValue, "remove"));
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);

        listeners.forEach(listener -> listener.notify(key, value, "get"));

        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
