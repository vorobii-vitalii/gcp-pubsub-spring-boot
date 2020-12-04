package com.gcp.example.services.impl;

import com.gcp.example.components.*;
import com.gcp.example.config.GCPConfig;
import com.gcp.example.model.Client;
import com.gcp.example.model.ClientMainInfo;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.GSMessage;
import com.gcp.example.repository.GoogleStorageRepository;
import com.gcp.example.services.MessageProcessor;
import com.google.cloud.bigquery.Job;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.bigquery.core.BigQueryException;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {
    private final AvroParser<Client> clientAvroParser;
    private final Mapper<Client, ClientMainInfo> clientMapper;
    private final AvroInputStreamConverter<Client> clientAvroInputStreamConverter;
    private final AvroInputStreamConverter<ClientMainInfo> clientMainInfoAvroInputStreamConverter;
    private final GCPConfig.BigQueryClientAvroFileGateway bigQueryClientAvroFileGateway;
    private final GoogleStorageRepository googleStorageRepository;

    @Override
    public void process(GSMessage googleStorageMessage) {
        final GoogleStorageResource resource = googleStorageRepository.getResource(googleStorageMessage);
        process(resource);
    }

    public void process(GoogleStorageResource gsResource) {
        try (InputStream inputStream = gsResource.getInputStream()) {

            final List<Client> clients = clientAvroParser.retrieve(inputStream);
            final List<ClientMainInfo> clientMainInfos = clients.stream()
                                                            .map(clientMapper::map)
                                                            .collect(Collectors.toList());

            try (InputStream clientStream =
                        clientAvroInputStreamConverter.convert(clients);
                 InputStream clientMainInfoStream =
                        clientMainInfoAvroInputStreamConverter.convert(clientMainInfos)) {

                final SettableListenableFuture<Job> future1 =
                    bigQueryClientAvroFileGateway.writeToBigQueryTable(clientStream, "Client");

                final SettableListenableFuture<Job> future2 =
                    bigQueryClientAvroFileGateway.writeToBigQueryTable(clientMainInfoStream, "Client_Main_Info");

                CompletableFuture.allOf(future1.completable(), future2.completable()).join();
                log.info("Data has been successfully inserted in a database");
            }

        }
        catch ( BigQueryException | IOException e) {
            log.error("Message processing has failed");
            e.printStackTrace();
            throw new MessageProcessingException();
        }
    }

}
