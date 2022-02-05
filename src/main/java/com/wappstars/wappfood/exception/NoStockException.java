package com.wappstars.wappfood.exception;

public class NoStockException extends RuntimeException {

    public NoStockException(Integer id, Integer stock) {
        super("Product with id:" + id + " only has " + stock + " left in stock.");
    }

}