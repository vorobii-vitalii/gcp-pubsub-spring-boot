package com.gcp.example.components.impl;

import com.gcp.example.components.Mapper;
import com.gcp.example.exceptions.MappingException;
import com.gcp.example.model.Body;
import com.gcp.example.model.GSMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BodyGSMessageMapperTest {

    @Autowired
    private Mapper<Body, GSMessage> bodyGSMessageMapper;

    @Test
    public void testMappingCorrectBody() {
        final String BUCKET_ID = "123";
        final String OBJECT_ID = "123";

        final Body body = new Body();
        final Body.Message message = new Body.Message();
        final Body.Message.MessageAttributes messageAttributes = new Body.Message.MessageAttributes();
        messageAttributes.setBucketId(BUCKET_ID);
        messageAttributes.setObjectId(OBJECT_ID);
        message.setAttributes(messageAttributes);
        body.setMessage(message);

        final GSMessage gsMessage = bodyGSMessageMapper.map(body);

        assertEquals(BUCKET_ID, gsMessage.getBucketId());
        assertEquals(OBJECT_ID, gsMessage.getObjectId());
    }

    @Test
    public void testMappingWrongBody() {
        final Body body = new Body();
        assertThrows(MappingException.class, () -> bodyGSMessageMapper.map(body));
    }


}