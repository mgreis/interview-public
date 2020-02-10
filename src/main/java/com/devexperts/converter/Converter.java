package com.devexperts.converter;

import java.util.Optional;

public interface Converter<I, O> {
    Optional<O> convert(I input);
}
