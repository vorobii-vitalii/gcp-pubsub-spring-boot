package com.gcp.example.components;

/**
 * Interface of Mapper design pattern
 */
public interface Mapper<T, U> {
    U map(T t);
}
