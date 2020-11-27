package com.gcp.example.components.impl;

import com.gcp.example.components.AvroInputStreamConverter;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Stands for converting Avro Object to Input Stream in order to pass it to BigQuery for processing
 */
@Service
public class GenericAvroInputStreamConverter<T extends SpecificRecord> implements AvroInputStreamConverter<T> {
    @Override
    public InputStream convert(T t) throws IOException {
        DatumWriter<T> datumWriter = new SpecificDatumWriter<T>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(t.getSchema(), outputStream);
        dataFileWriter.append(t);
        dataFileWriter.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
