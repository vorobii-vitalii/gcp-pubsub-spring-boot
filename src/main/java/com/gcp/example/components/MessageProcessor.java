package com.gcp.example.components;

import com.gcp.example.model.Body;

/**
 * Message Processor abstraction
 */
public interface MessageProcessor {
    void process(Body.Message.MessageAttributes payload);
}
