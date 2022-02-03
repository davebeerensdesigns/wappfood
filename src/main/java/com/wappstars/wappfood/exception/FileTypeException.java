package com.wappstars.wappfood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileTypeException extends RuntimeException {
    private String message;
}

