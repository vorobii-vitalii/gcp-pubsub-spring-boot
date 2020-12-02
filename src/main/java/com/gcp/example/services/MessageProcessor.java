package com.gcp.example.services;

import com.gcp.example.model.Body;
import com.gcp.example.model.GSMessage;

/**
 * Message Processor abstraction
 */
public interface MessageProcessor {
    void process(GSMessage googleStorageMessage);
}
