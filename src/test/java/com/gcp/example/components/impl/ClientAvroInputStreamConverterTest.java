package com.gcp.example.components.impl;

import com.gcp.example.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

@SpringBootTest
class ClientAvroInputStreamConverterTest {
    private InputStream correctInputStream;

    @Autowired
    private GenericAvroInputStreamConverter<Client> avroInputStreamConverter;

    @BeforeEach
    public void init() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("users4.avro")).getFile());
        correctInputStream = new FileInputStream(file);
    }

    @Test
    public void test() throws IOException {
        final Client client = Client.newBuilder()
                .setId(4)
                .setAddress("IF")
                .setName("Vitalii")
                .setPhone("102")
                .build();

        final InputStream obtainedInputStream = avroInputStreamConverter.convert(client);
        checkForIdentity(obtainedInputStream, correctInputStream);
    }

    private void checkForIdentity(InputStream obtainedInputStream, InputStream correctInputStream) throws IOException {
        byte [] obtainedBytes = obtainedInputStream.readAllBytes();
        byte [] correctBytes = correctInputStream.readAllBytes();

        final int N = obtainedBytes.length;

        boolean areIdentical = true;

        for (int i = 0; i < N; i++) {
            if (obtainedBytes[i] == correctBytes[i]) {
                if (obtainedBytes[i] == 0) {
                    break;
                }
            } else {
                areIdentical = false;
                break;
            }
        }

        assertTrue(areIdentical);
    }

}