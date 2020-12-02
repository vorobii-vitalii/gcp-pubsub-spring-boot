package com.gcp.example.components.impl;

import com.gcp.example.components.AvroParser;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.Client;
import com.gcp.example.model.GSMessage;
import com.gcp.example.repository.GoogleStorageRepository;
import com.gcp.example.services.impl.MessageProcessorImpl;
import com.google.cloud.storage.Storage;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private GoogleStorageRepository googleStorageRepository;

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

}