package com.gcp.example.components.impl;

import com.gcp.example.components.AvroParser;
import com.gcp.example.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientAvroParserTest {
    private InputStream inputStream;

    @Autowired
    private AvroParser<Client> clientAvroParser;

    @BeforeEach
    public void init() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("users4.avro").getFile());
        inputStream = new FileInputStream(file);
    }

    @Test
    public void testParseCorrectFile() throws IOException {
        final List<Client> clients = clientAvroParser.retrieve(inputStream);

        assertEquals(1, clients.size());

        final Client client = clients.get(0);

        assertEquals(4L, client.getId());
        assertEquals("Vitalii", client.getName().toString());
        assertEquals("102", client.getPhone().toString());
        assertEquals("IF", client.getAddress().toString());
    }

    @Test
    public void testWrongFileThrowsException() {
        InputStream corruptedInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});
        assertThrows(IOException.class, () -> clientAvroParser.retrieve(corruptedInputStream));
    }

}