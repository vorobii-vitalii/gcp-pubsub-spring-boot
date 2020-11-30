package com.gcp.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BodyTest {

    private final String inputPlainText =
            "{\n" +
            "   \"message\":{\n" +
            "      \"attributes\":{\n" +
            "         \"bucketId\":\"example-avro-files\",\n" +
            "         \"eventTime\":\"2020-11-30T10:36:53.250744Z\",\n" +
            "         \"eventType\":\"OBJECT_FINALIZE\",\n" +
            "         \"notificationConfig\":\"projects/_/buckets/example-avro-files/notificationConfigs/1\",\n" +
            "         \"objectGeneration\":\"1606732613250883\",\n" +
            "         \"objectId\":\"users9.avro\",\n" +
            "         \"overwroteGeneration\":\"1606409857667539\",\n" +
            "         \"payloadFormat\":\"JSON_API_V1\"\n" +
            "      },\n" +
            "      \"data\":\"ewogICJraW5kIjogInN0b3JhZ2Ujb2JqZWN0IiwKICAiaWQiOiAiZXhhbXBsZS1hdnJvLWZpbGVzL3VzZXJzOS5hdnJvLzE2MDY3MzI2MTMyNTA4ODMiLAogICJzZWxmTGluayI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9zdG9yYWdlL3YxL2IvZXhhbXBsZS1hdnJvLWZpbGVzL28vdXNlcnM5LmF2cm8iLAogICJuYW1lIjogInVzZXJzOS5hdnJvIiwKICAiYnVja2V0IjogImV4YW1wbGUtYXZyby1maWxlcyIsCiAgImdlbmVyYXRpb24iOiAiMTYwNjczMjYxMzI1MDg4MyIsCiAgIm1ldGFnZW5lcmF0aW9uIjogIjEiLAogICJjb250ZW50VHlwZSI6ICJhcHBsaWNhdGlvbi9vY3RldC1zdHJlYW0iLAogICJ0aW1lQ3JlYXRlZCI6ICIyMDIwLTExLTMwVDEwOjM2OjUzLjI1MFoiLAogICJ1cGRhdGVkIjogIjIwMjAtMTEtMzBUMTA6MzY6NTMuMjUwWiIsCiAgInN0b3JhZ2VDbGFzcyI6ICJTVEFOREFSRCIsCiAgInRpbWVTdG9yYWdlQ2xhc3NVcGRhdGVkIjogIjIwMjAtMTEtMzBUMTA6MzY6NTMuMjUwWiIsCiAgInNpemUiOiAiMjg4IiwKICAibWQ1SGFzaCI6ICJCdHJRbGFCc2pMWDlVYTVBdktoamFnPT0iLAogICJtZWRpYUxpbmsiOiAiaHR0cHM6Ly93d3cuZ29vZ2xlYXBpcy5jb20vZG93bmxvYWQvc3RvcmFnZS92MS9iL2V4YW1wbGUtYXZyby1maWxlcy9vL3VzZXJzOS5hdnJvP2dlbmVyYXRpb249MTYwNjczMjYxMzI1MDg4MyZhbHQ9bWVkaWEiLAogICJjcmMzMmMiOiAiSk52dndnPT0iLAogICJldGFnIjogIkNNUGVwYldKcXUwQ0VBRT0iCn0K\",\n" +
            "      \"messageId\":\"1785942290937013\",\n" +
            "      \"message_id\":\"1785942290937013\",\n" +
            "      \"publishTime\":\"2020-11-30T10:36:54.231Z\",\n" +
            "      \"publish_time\":\"2020-11-30T10:36:54.231Z\"\n" +
            "   },\n" +
            "   \"subscription\":\"projects/ageless-aleph-296613/subscriptions/avo-rest-processing\"\n" +
            "}";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testObjectMapping() throws JsonProcessingException {
        final Body body = objectMapper.readValue(inputPlainText, Body.class);
        assertEquals("example-avro-files", body.getMessage().getAttributes().getBucketId());
        assertEquals("users9.avro", body.getMessage().getAttributes().getObjectId());
    }

}