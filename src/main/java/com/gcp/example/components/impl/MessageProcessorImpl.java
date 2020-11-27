package com.gcp.example.components.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcp.example.components.AvroInputStreamConverter;
import com.gcp.example.components.AvroParser;
import com.gcp.example.components.Mapper;
import com.gcp.example.config.GCPConfig;
import com.gcp.example.model.Client;
import com.gcp.example.model.ClientMainInfo;
import com.gcp.example.model.MessageInfo;
import com.gcp.example.components.MessageProcessor;
import com.gcp.example.exceptions.MessageProcessingException;
import com.google.cloud.bigquery.Job;
import com.google.cloud.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.cloud.gcp.bigquery.core.BigQueryException;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {
    private static final String GOOGLE_STORAGE_PREFIX = "gs://";

    private final ObjectMapper objMapper;
    private final Storage storage;
    private final AvroParser<Client> clientAvroParser;
    private final Mapper<Client, ClientMainInfo> clientMapper;
    private final AvroInputStreamConverter<SpecificRecord> avroInputStreamConverter;
    private final GCPConfig.BigQueryClientAvroFileGateway bigQueryClientAvroFileGateway;

    @Override
    public void process(String payload) {

        try {
            final MessageInfo messageInfo = objMapper.readValue(payload, MessageInfo.class);
            log.info("Received message: " + messageInfo);

            final String resourceUrl = formGoogleStorageLink(messageInfo);
            final Resource resource = new GoogleStorageResource(storage, resourceUrl);
            final InputStream inputStream = resource.getInputStream();

            final Client client = clientAvroParser.retrieve(inputStream);
            final ClientMainInfo clientMainInfo = clientMapper.map(client);

            final InputStream clientStream = avroInputStreamConverter.convert(client);
            final InputStream clientMainInfoStream = avroInputStreamConverter.convert(clientMainInfo);

            final SettableListenableFuture<Job> future1 =
                bigQueryClientAvroFileGateway.writeToBigQueryTable(clientStream, "Client");

            final SettableListenableFuture<Job> future2 =
                bigQueryClientAvroFileGateway.writeToBigQueryTable(clientMainInfoStream, "Client_Main_Info");

            CompletableFuture.allOf(future1.completable(), future2.completable()).join();
            log.info("Data has been successfully inserted in a database");
        }
        catch (JsonProcessingException | BigQueryException e) {
            log.error("Message processing error or error while executing query");
            e.printStackTrace();
        }
        catch (IOException  ignored) {
            log.error("Unknown error occurred during processing");
            throw new MessageProcessingException();
        }
    }

    private String formGoogleStorageLink(MessageInfo messageInfo) {
        return  GOOGLE_STORAGE_PREFIX +
                messageInfo.getBucket() +
                "/" +
                messageInfo.getName();
    }
}
