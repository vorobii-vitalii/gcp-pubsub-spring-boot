package com.gcp.example.components.impl;

import com.gcp.example.components.AvroInputStreamConverter;
import com.gcp.example.components.AvroParser;
import com.gcp.example.config.GCPConfig;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.Body;
import com.gcp.example.model.Client;
import com.google.cloud.storage.Storage;
import org.apache.avro.specific.SpecificRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageProcessorImplTest {
    private Body.Message.MessageAttributes messageAttributes;

    @MockBean
    private Storage mockStorage;

    @Autowired
    private MessageProcessorImpl messageProcessor;

    @MockBean
    private AvroInputStreamConverter<SpecificRecord> avroInputStreamConverter;

    @MockBean
    private AvroParser<Client> clientAvroParser;

    @MockBean
    private GCPConfig.BigQueryClientAvroFileGateway bigQueryClientAvroFileGateway;

    @BeforeEach
    public void setUp() {
        messageAttributes = new Body.Message.MessageAttributes();
        messageAttributes.setBucketId("123");
        messageAttributes.setObjectId("test.avro");
    }

    @Test
    void testThrowIOExceptionWhenObjectNotFound() {
        final String generatedLink = messageProcessor.formGoogleStorageLink(messageAttributes);
        when(mockStorage.get(messageAttributes.getObjectId())).thenReturn(null);
        assertThrows(FileNotFoundException.class, () ->
                new GoogleStorageResource(mockStorage, generatedLink).getInputStream()
        );
    }

    @Test
    void testThrowCustomExceptionFromMethodWhenInputStreamIsCorrupted() {
        when(mockStorage.get(messageAttributes.getObjectId())).thenReturn(null);
        assertThrows(MessageProcessingException.class, () ->
                messageProcessor.process(messageAttributes)
        );
    }

    @Test
    void testCorruptedInputStreamProducesIOException() throws IOException {
        final InputStream expectedInputStream = new ByteArrayInputStream(new byte[] {1, 2, 3});

        GoogleStorageResource mockGSR = mock(GoogleStorageResource.class);
        when(mockGSR.getInputStream()).thenReturn(expectedInputStream);

        when(clientAvroParser.retrieve(expectedInputStream)).thenThrow(IOException.class);

        assertThrows(MessageProcessingException.class, () ->
                messageProcessor.process(messageAttributes)
        );
    }

//    @Test
//    void testCorrectInputStreamProducesSuccessfulMethodExecution() throws IOException {
//        final Client client = new Client(1L, "George", "123", "New York");
//        final ClientMainInfo clientMainInfo = new ClientMainInfo(1L, "George");
//
//        final InputStream expectedInputStream = new ByteArrayInputStream(new byte[] {1, 2, 3});
//
//        GoogleStorageResource mockGSR = mock(GoogleStorageResource.class);
//        when(mockGSR.getInputStream()).thenReturn(expectedInputStream);
//
//        when(clientAvroParser.retrieve(expectedInputStream)).thenReturn(client);
//
//
//        final InputStream clientInputStream = new ByteArrayInputStream(new byte[] {1, 2, 3});
//        final InputStream clientMainInfoInputStream = new ByteArrayInputStream(new byte[] {4, 5, 6});
//
//        when(avroInputStreamConverter.convert(client)).thenReturn(clientInputStream);
//        when(avroInputStreamConverter.convert(clientMainInfo)).thenReturn(clientMainInfoInputStream);
//
//        final SettableListenableFuture<Job> future = new SettableListenableFuture<>();
//        future.set(null);
//
//        when(bigQueryClientAvroFileGateway.writeToBigQueryTable(clientInputStream, "Client"))
//                .thenReturn(future);
//
//        when(bigQueryClientAvroFileGateway.writeToBigQueryTable(clientMainInfoInputStream, "Client_Main_Info"))
//                .thenReturn(future);
//
//        messageProcessor.process(mockGSR);
//        // Everything should run fine
//    }

    @Test
    void testFormGoogleStorageURI() {
        final String generatedLink = messageProcessor.formGoogleStorageLink(messageAttributes);
        assertEquals("gs://123/test.avro", generatedLink);
    }

}