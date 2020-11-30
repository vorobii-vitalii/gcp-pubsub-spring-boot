package com.gcp.example.model;

import lombok.Data;

@Data
public class Body {
    private Message message;

    @Data
    public static class Message {
        private MessageAttributes attributes;

        @Data
        public static class MessageAttributes {
            private String bucketId;
            private String objectId;
        }
    }
}
