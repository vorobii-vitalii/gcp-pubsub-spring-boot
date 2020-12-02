package com.gcp.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcp.example.components.Mapper;
import com.gcp.example.components.impl.BodyGSMessageMapper;
import com.gcp.example.model.GSMessage;
import com.gcp.example.services.MessageProcessor;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.Body;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {
    private static final String CORRECT_OBJECT_ID = "someObjId";
    private static final String CORRECT_BUCKET_ID = "someBucketId";

    private static final String WRONG_OBJECT_ID = "someWrongObjId";
    private static final String WRONG_BUCKET_ID = "someWrongBucketId";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageProcessor messageProcessor;

    @Autowired
    private Mapper<Body, GSMessage> bodyGSMessageMapper;

    private Body correctBody;
    private Body wrongBody;

    @BeforeEach
    public void setup() {
        setCorrectBody();
        setWrongBody();
    }

    private void setCorrectBody() {
        Body correctBody = new Body();
        Body.Message message = new Body.Message();
        Body.Message.MessageAttributes messageAttributes = new Body.Message.MessageAttributes();
        messageAttributes.setObjectId(CORRECT_OBJECT_ID);
        messageAttributes.setBucketId(CORRECT_BUCKET_ID);
        message.setAttributes(messageAttributes);
        correctBody.setMessage(message);
        this.correctBody = correctBody;
    }

    private void setWrongBody() {
        Body wrongBody = new Body();
        Body.Message message = new Body.Message();
        Body.Message.MessageAttributes messageAttributes = new Body.Message.MessageAttributes();
        messageAttributes.setObjectId(WRONG_OBJECT_ID);
        messageAttributes.setBucketId(WRONG_BUCKET_ID);
        message.setAttributes(messageAttributes);
        wrongBody.setMessage(message);
        this.wrongBody = wrongBody;
    }

    @Test
    public void testCorrectPostRequest() throws Exception {
        final String json = objectMapper.writeValueAsString(correctBody);
        final GSMessage gsMessage = bodyGSMessageMapper.map(correctBody);

        doNothing()
                .when(messageProcessor)
                .process(gsMessage);

        mvc.perform(
                post("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testWrongPostRequest() throws Exception {
        final String json = objectMapper.writeValueAsString(wrongBody);
        final GSMessage gsMessage = bodyGSMessageMapper.map(wrongBody);

        doThrow(MessageProcessingException.class)
                .when(messageProcessor).process(gsMessage);

        mvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}