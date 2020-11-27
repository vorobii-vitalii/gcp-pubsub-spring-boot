package com.gcp.example.components;

public interface Mapper<T, U> {
    U map(T t);
}
