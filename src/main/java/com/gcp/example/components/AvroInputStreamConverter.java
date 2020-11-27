package com.gcp.example.components;

import org.apache.avro.specific.SpecificRecord;

import java.io.IOException;
import java.io.InputStream;

public interface AvroInputStreamConverter<T extends SpecificRecord> {
    InputStream convert(T t) throws IOException;
}
