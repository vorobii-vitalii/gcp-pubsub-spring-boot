package com.gcp.example.repository.impl;

import com.gcp.example.components.AvroParser;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoogleStorageRepositoryImplTest {
    private GSMessage gsMessage;

    @Autowired
    private GoogleStorageRepositoryImpl googleStorageRepository;

    @BeforeEach
    public void setUp() {
        gsMessage = new GSMessage();
        gsMessage.setBucketId("123");
        gsMessage.setObjectId("test.avro");
    }

    @Test
    void formGoogleStorageLink() {
        final String expectedLink = "gs://123/test.avro";
        final String actualLink = googleStorageRepository.formGoogleStorageLink(gsMessage);
        assertEquals(expectedLink, actualLink);
    }

    @Test
    void verifyResourceBucketNameCorrectness() {
        final GoogleStorageResource gsResource = googleStorageRepository.getResource(gsMessage);
        assertEquals("123", gsResource.getBucketName());
    }

}
