package com.devexperts.validator;

public interface Validator<I> {
    boolean isValid(I input);
}
