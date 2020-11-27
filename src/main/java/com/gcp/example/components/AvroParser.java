package com.gcp.example.components;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.io.DatumReader;

import java.io.IOException;
import java.io.InputStream;


public abstract class AvroParser<T> {
    protected abstract DatumReader<T> getDatumReader();

    public T retrieve(InputStream inputStream) throws IOException {
        DataFileStream<T> fileStream = new DataFileStream<>(inputStream, getDatumReader());
        T t = null;
        if (fileStream.hasNext()) {
            t = fileStream.next(t);
        }
        return t;
    }
}
