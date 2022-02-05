package com.wappstars.wappfood.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class NoStockException extends RuntimeException {

    public NoStockException(Integer id, Integer stock) {
        super("Product with id:" + id + " only has " + stock + " left in stock.");
    }

}