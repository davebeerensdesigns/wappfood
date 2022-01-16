package com.wappstars.wappfood.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class OptionDoesNotExistException extends RuntimeException {

    public OptionDoesNotExistException(Class clazz, String values, String... searchParamsMap) {
        super(OptionDoesNotExistException.generateMessage(clazz.getSimpleName(), values, toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, String values, Map<String, String> searchParams) {
        return "Value " +
                searchParams +
                " for " +
                StringUtils.capitalize(entity) +
                " does not exist. Please choose from: " +
                values;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

}