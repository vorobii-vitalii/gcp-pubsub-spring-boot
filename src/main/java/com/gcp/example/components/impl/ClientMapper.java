package com.gcp.example.components.impl;

import com.gcp.example.components.Mapper;
import com.gcp.example.model.Client;
import com.gcp.example.model.ClientMainInfo;
import org.springframework.stereotype.Component;

/**
 * Implementation of Client mapper to ClientMainInfo
 * @see Client
 * @see ClientMainInfo
 */
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
