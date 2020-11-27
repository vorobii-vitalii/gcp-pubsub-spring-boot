package com.gcp.example.components.impl;

import com.gcp.example.components.AvroParser;
import example.gcp.Client;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.stereotype.Service;

@Service
public class ClientAvroParser extends AvroParser<Client> {

    @Override
    public DatumReader<Client> getDatumReader() {
        return new SpecificDatumReader<>(Client.class);
    }
}
