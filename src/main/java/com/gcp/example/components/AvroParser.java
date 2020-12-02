package com.gcp.example.components;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.io.DatumReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public abstract class AvroParser<T> {
    protected abstract DatumReader<T> getDatumReader();

    public List<T> retrieve(InputStream inputStream) throws IOException {
        DataFileStream<T> fileStream = new DataFileStream<>(inputStream, getDatumReader());
        final List<T> result = new ArrayList<>();
        T t = null;
        if (fileStream.hasNext()) {
            t = fileStream.next(t);
            result.add(t);
        }
        return result;
    }
}
