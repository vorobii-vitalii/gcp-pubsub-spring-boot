package com.gcp.example.components.impl;

import com.gcp.example.components.AvroInputStreamConverter;
import com.gcp.example.components.AvroParser;
import com.gcp.example.components.Mapper;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.Client;
import com.gcp.example.model.ClientMainInfo;
import com.gcp.example.model.GSMessage;
import com.gcp.example.repository.impl.GoogleStorageRepositoryImpl;
import com.gcp.example.services.impl.MessageProcessorImpl;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.gcp.bigquery.core.BigQueryException;
import org.springframework.cloud.gcp.bigquery.core.BigQueryTemplate;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MessageProcessorImplTest {
    private GSMessage gsMessage;

    @MockBean
    private Storage mockStorage;

    @Autowired
    private MessageProcessorImpl messageProcessor;

    @MockBean
    private AvroParser<Client> clientAvroParser;

    @Autowired
    private GoogleStorageRepositoryImpl googleStorageRepository;

    @Autowired
    private Mapper<Client, ClientMainInfo> clientMapper;

    @MockBean
    private AvroInputStreamConverter<Client> clientAvroInputStreamConverter;

    @MockBean
    private AvroInputStreamConverter<ClientMainInfo> clientMainInfoAvroInputStreamConverter;

    @MockBean
    private BigQueryTemplate bigQueryTemplate;

    @BeforeEach
    public void setUp() {
        gsMessage = new GSMessage();
        gsMessage.setBucketId("123");
        gsMessage.setObjectId("test.avro");
    }

    @Test
    void testThrowIOExceptionWhenObjectNotFound() {
        when(mockStorage.get(gsMessage.getObjectId())).thenReturn(null);
        assertThrows(FileNotFoundException.class, () ->
                googleStorageRepository.getResource(gsMessage).getInputStream()
        );
    }

    @Test
    void testThrowCustomExceptionFromMethodWhenInputStreamIsCorrupted() {
        when(mockStorage.get(gsMessage.getObjectId())).thenReturn(null);
        assertThrows(MessageProcessingException.class, () ->
                messageProcessor.process(gsMessage)
        );
    }

    @Test
    void testCorruptedInputStreamProducesIOException() throws IOException {
        final InputStream expectedInputStream = new ByteArrayInputStream(new byte[] {1, 2, 3});

        GoogleStorageResource mockGSR = mock(GoogleStorageResource.class);
        when(mockGSR.getInputStream()).thenReturn(expectedInputStream);

        when(clientAvroParser.retrieve(expectedInputStream)).thenThrow(IOException.class);

        assertThrows(MessageProcessingException.class, () ->
                messageProcessor.process(gsMessage)
        );
    }

    @Test
    void assertExecutesFine() throws IOException {

        final InputStream inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});

        final Client client = Client
                                .newBuilder()
                                .setId(1L)
                                .setName("John")
                                .setAddress("New York")
                                .setPhone("123")
                                .build();

        final List<Client> expectedListOfClients = List.of(client);
        final List<ClientMainInfo> expectedListOfClientMainInfos = expectedListOfClients.stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());

        GoogleStorageResource resource = spy(googleStorageRepository.getResource(gsMessage));

        doReturn(inputStream).when(resource).getInputStream();

        when(clientAvroParser.retrieve(inputStream)).thenReturn(expectedListOfClients);

        when(clientAvroInputStreamConverter.convert(expectedListOfClients)).thenReturn(inputStream);
        when(clientMainInfoAvroInputStreamConverter.convert(expectedListOfClientMainInfos)).thenReturn(inputStream);

        final SettableListenableFuture<Job> future1 = new SettableListenableFuture<>();

        future1.set(null);

        when(bigQueryTemplate.writeDataToTable("Client", inputStream, FormatOptions.avro())).thenReturn(future1);
        when(bigQueryTemplate.writeDataToTable("Client_Main_Info", inputStream, FormatOptions.avro())).thenReturn(future1);

        messageProcessor.process(resource);

        // should run fine
    }

    @Test
    void test() throws IOException, InterruptedException, ExecutionException {

        final InputStream inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});

        final Client client = Client
                .newBuilder()
                .setId(1L)
                .setName("John")
                .setAddress("New York")
                .setPhone("123")
                .build();

        final List<Client> expectedListOfClients = List.of(client);
        final List<ClientMainInfo> expectedListOfClientMainInfos = expectedListOfClients.stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());

        GoogleStorageResource resource = spy(googleStorageRepository.getResource(gsMessage));

        doReturn(inputStream).when(resource).getInputStream();

        when(clientAvroParser.retrieve(inputStream)).thenReturn(expectedListOfClients);

        when(clientAvroInputStreamConverter.convert(expectedListOfClients)).thenReturn(inputStream);
        when(clientMainInfoAvroInputStreamConverter.convert(expectedListOfClientMainInfos)).thenReturn(inputStream);

        final SettableListenableFuture<Job> future1 = new SettableListenableFuture<>();

        when(bigQueryTemplate.writeDataToTable("Client", inputStream, FormatOptions.avro())).thenReturn(future1);
        when(bigQueryTemplate.writeDataToTable("Client_Main_Info", inputStream, FormatOptions.avro())).thenReturn(future1);

        new Thread(() -> {
            try {
                Thread.sleep(7_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future1.set(null);
        }).start();

        messageProcessor.process(resource);
        Thread.sleep(2000);
        assertNull(future1.get());
    }

    @Test
    void assertMethodRunsTillAllDataIsWrittenInDB() throws IOException, InterruptedException {

        final InputStream inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});

        final Client client = Client
                .newBuilder()
                .setId(1L)
                .setName("John")
                .setAddress("New York")
                .setPhone("123")
                .build();

        final List<Client> expectedListOfClients = List.of(client);
        final List<ClientMainInfo> expectedListOfClientMainInfos = expectedListOfClients.stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());

        GoogleStorageResource resource = spy(googleStorageRepository.getResource(gsMessage));

        doReturn(inputStream).when(resource).getInputStream();

        when(clientAvroParser.retrieve(inputStream)).thenReturn(expectedListOfClients);

        when(clientAvroInputStreamConverter.convert(expectedListOfClients)).thenReturn(inputStream);
        when(clientMainInfoAvroInputStreamConverter.convert(expectedListOfClientMainInfos)).thenReturn(inputStream);

        final SettableListenableFuture<Job> future1 = new SettableListenableFuture<>();

        future1.set(null);

        when(bigQueryTemplate.writeDataToTable("Client", inputStream, FormatOptions.avro())).thenReturn(future1);
        when(bigQueryTemplate.writeDataToTable("Client_Main_Info", inputStream, FormatOptions.avro())).thenReturn(null);

        Thread processingThread = new Thread(() -> {
            messageProcessor.process(resource);
        });

        processingThread.start();

        Thread.sleep(2000);

        assertEquals(processingThread.getState(),Thread.State.WAITING);
    }

    @Test
    void assertMethodThrowsExceptionWhenWriterProvidesNoJob() throws IOException, InterruptedException {

        final InputStream inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});

        final Client client = Client
                .newBuilder()
                .setId(1L)
                .setName("John")
                .setAddress("New York")
                .setPhone("123")
                .build();

        final List<Client> expectedListOfClients = List.of(client);
        final List<ClientMainInfo> expectedListOfClientMainInfos = expectedListOfClients.stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());

        GoogleStorageResource resource = spy(googleStorageRepository.getResource(gsMessage));

        doReturn(inputStream).when(resource).getInputStream();

        when(clientAvroParser.retrieve(inputStream)).thenReturn(expectedListOfClients);

        when(clientAvroInputStreamConverter.convert(expectedListOfClients)).thenReturn(inputStream);
        when(clientMainInfoAvroInputStreamConverter.convert(expectedListOfClientMainInfos)).thenReturn(inputStream);

        final SettableListenableFuture<Job> future1 = new SettableListenableFuture<>();

        future1.set(null);

        when(bigQueryTemplate.writeDataToTable("Client", inputStream, FormatOptions.avro())).thenThrow(BigQueryException.class);
        when(bigQueryTemplate.writeDataToTable("Client_Main_Info", inputStream, FormatOptions.avro())).thenReturn(null);

        assertThrows(MessageProcessingException.class, () ->
                messageProcessor.process(resource));

    }




}