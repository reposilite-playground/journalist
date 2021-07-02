package net.dzikoysk.dynamiclogger.utils;

import java.util.AbstractMap;
import java.util.Map.Entry;

public final class EntryUtils {

    private EntryUtils() {}

    public static <K, V> Entry<K, V> entryOf(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

}
