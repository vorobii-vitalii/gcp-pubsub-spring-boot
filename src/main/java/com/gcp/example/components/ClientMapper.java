package com.gcp.example.components;

import example.gcp.Client;
import example.gcp.ClientMainInfo;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper implements Mapper<Client, ClientMainInfo> {

    @Override
    public ClientMainInfo map(Client client) {
        if (client == null)
            return null;
        return ClientMainInfo
                .newBuilder()
                .setId(client.getId())
                .setName(client.getName())
                .build();
    }
}