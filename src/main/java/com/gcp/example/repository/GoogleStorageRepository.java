package com.gcp.example.repository;

import com.gcp.example.model.GSMessage;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;

public interface GoogleStorageRepository {
    GoogleStorageResource getResource(GSMessage googleStorageMessage);
}
