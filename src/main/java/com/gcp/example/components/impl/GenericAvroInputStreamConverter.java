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
import java.util.List;

/**
 * Stands for converting Avro Object to Input Stream in order to pass it to BigQuery for processing
 */
@Service
public class GenericAvroInputStreamConverter<T extends SpecificRecord> implements AvroInputStreamConverter<T> {
    @Override
    public InputStream convert(List<T> avroObjectList) throws IOException {
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(datumWriter);
        // Append schema
        if (!avroObjectList.isEmpty()) {
            dataFileWriter.create(avroObjectList.get(0).getSchema(), outputStream);
        }
        // Append objects
        for (T t : avroObjectList) {
            dataFileWriter.append(t);
        }
        dataFileWriter.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
