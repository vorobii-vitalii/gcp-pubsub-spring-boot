package com.gcp.example.config;

import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.bigquery.core.BigQueryTemplate;
import org.springframework.cloud.gcp.bigquery.integration.BigQuerySpringMessageHeaders;
import org.springframework.cloud.gcp.bigquery.integration.outbound.BigQueryFileMessageHandler;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.InputStream;
import java.time.Duration;

@Configuration
@Slf4j
public class GCPConfig {

//    @Bean
//    public PubSubInboundChannelAdapter messageChannelAdapter(
//            @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
//            PubSubTemplate pubSubTemplate) {
//        PubSubInboundChannelAdapter adapter =
//                new PubSubInboundChannelAdapter(pubSubTemplate, "AVRO_PROCESSOR");
//        adapter.setOutputChannel(inputChannel);
//        adapter.setAckMode(AckMode.MANUAL);
//
//        return adapter;
//    }
//
//    @Bean
//    public MessageChannel pubsubInputChannel() {
//        return new DirectChannel();
//    }

    @Bean
    public DirectChannel bigQueryWriteDataChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel bigQueryJobReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "bigQueryWriteDataChannel")
    public MessageHandler messageSender(BigQueryTemplate bigQueryTemplate) {
        BigQueryFileMessageHandler messageHandler = new BigQueryFileMessageHandler(bigQueryTemplate);
        messageHandler.setFormatOptions(FormatOptions.avro());
        messageHandler.setOutputChannel(bigQueryJobReplyChannel());
        messageHandler.setSync(false);
        messageHandler.setTimeout(Duration.ofSeconds(5));
        return messageHandler;
    }

    @Bean
    @Primary
    public GatewayProxyFactoryBean gatewayProxyFactoryBean() {
        GatewayProxyFactoryBean factoryBean = new GatewayProxyFactoryBean(BigQueryClientAvroFileGateway.class);
        factoryBean.setDefaultRequestChannel(bigQueryWriteDataChannel());
        factoryBean.setDefaultReplyChannel(bigQueryJobReplyChannel());
        return factoryBean;
    }

    /**
     * Spring Integration gateway which allows sending data to load to BigQuery through a
     * channel.
     */
    @MessagingGateway
    public interface BigQueryClientAvroFileGateway {
        SettableListenableFuture<Job> writeToBigQueryTable(
                InputStream inputStream, @Header(BigQuerySpringMessageHeaders.TABLE_NAME) String tableName);
    }

}
