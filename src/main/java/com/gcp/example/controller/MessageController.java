package com.gcp.example.controller;

import com.gcp.example.components.MessageProcessor;
import com.gcp.example.exceptions.MessageProcessingException;
import com.gcp.example.model.Body;
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

    @PostMapping("/")
    public ResponseEntity<String> handleMessageReceived (
            @RequestBody Body body)
    {
        try {
            processor.process(body.getMessage().getAttributes());
        }
        catch (MessageProcessingException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Good", HttpStatus.OK);
    }

}
