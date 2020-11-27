package com.gcp.example.components;

import example.gcp.Client;
import example.gcp.ClientMainInfo;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientMapperTest {

    @Autowired
    private Mapper<Client, ClientMainInfo> clientMapper;

    @Test
    void map() {
        final Client client = Client
                .newBuilder()
                .setId(2)
                .setName("John")
                .setPhone("123")
                .setAddress("New York")
                .build();
        final ClientMainInfo clientMainInfo = clientMapper.map(client);

        assertEquals(client.getId(), clientMainInfo.getId());
        assertEquals(client.getName(), clientMainInfo.getName());
    }
}