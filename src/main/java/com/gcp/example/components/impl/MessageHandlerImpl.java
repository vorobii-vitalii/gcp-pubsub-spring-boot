package com.gcp.example.components.impl;

import com.gcp.example.components.MessageProcessor;
import com.gcp.example.exceptions.MessageProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MessageHandlerImpl implements MessageHandler {
    private final MessageProcessor messageProcessor;

    /**
     * If delivered message was processed successfully or the format is unacceptable
     * the server will acknowledge the message
     * In other case it will continue to accept that
     */
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        final String payload = new String((byte[]) message.getPayload());
        messageProcessor.process(payload);
        answerBack(message);
    }

    private void answerBack(Message<?> message) {
        BasicAcknowledgeablePubsubMessage originalMessage =
                message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
        if (originalMessage != null)
            originalMessage.ack();
    }

}
