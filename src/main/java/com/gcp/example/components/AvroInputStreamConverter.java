package com.gcp.example.components;

import org.apache.avro.specific.SpecificRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AvroInputStreamConverter<T extends SpecificRecord> {
    InputStream convert(List<T> avroObjectList) throws IOException;
}
