package com.gcp.example.components;

/**
 * Message Processor abstraction
 */
public interface MessageProcessor {
    void process(String payload);
}
