package com.gcp.example.controller;

import com.gcp.example.components.Mapper;
import com.gcp.example.services.MessageProcessor;
import com.gcp.example.exceptions.MappingException;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.Body;
import com.gcp.example.model.GSMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class MessageController {
    private final MessageProcessor processor;
    private final Mapper<Body, GSMessage> bodyGSMessageMapper;

    @PostMapping("/")
    public ResponseEntity<String> handleMessageReceived (@RequestBody Body body) {
        try {
            var map = bodyGSMessageMapper.map(body);
            processor.process(map);
        }
        catch (MessageProcessingException | MappingException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Good", HttpStatus.OK);
    }

}
