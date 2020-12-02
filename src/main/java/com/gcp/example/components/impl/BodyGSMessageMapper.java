package com.gcp.example.components.impl;

import com.gcp.example.components.Mapper;
import com.gcp.example.exceptions.MappingException;
import com.gcp.example.model.Body;
import com.gcp.example.model.GSMessage;
import org.springframework.stereotype.Component;

@Component
public class BodyGSMessageMapper implements Mapper<Body, GSMessage> {
    @Override
    public GSMessage map(Body body) {
        try {
            GSMessage gsMessage = new GSMessage();
            gsMessage.setBucketId(body.getMessage().getAttributes().getBucketId());
            gsMessage.setObjectId(body.getMessage().getAttributes().getObjectId());
            return gsMessage;
        }
        catch (NullPointerException npe) {
            throw new MappingException("Unable to map Body to GSMessage");
        }
    }
}
