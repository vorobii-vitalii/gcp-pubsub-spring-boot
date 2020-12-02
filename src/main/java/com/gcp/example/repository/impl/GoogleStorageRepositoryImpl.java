package com.gcp.example.repository.impl;

import com.gcp.example.repository.GoogleStorageRepository;
import com.gcp.example.model.GSMessage;
import com.google.cloud.storage.Storage;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GoogleStorageRepositoryImpl implements GoogleStorageRepository {
    private static final String GOOGLE_STORAGE_PREFIX = "gs://";
    private final Storage storage;

    @Override
    public GoogleStorageResource getResource(GSMessage googleStorageMessage) {
        final String googleStorageLink = formGoogleStorageLink(googleStorageMessage);
        return new GoogleStorageResource(storage, googleStorageLink);
    }

    public String formGoogleStorageLink(GSMessage googleStorageMessage) {
        return  GOOGLE_STORAGE_PREFIX +
                googleStorageMessage.getBucketId() +
                "/" +
                googleStorageMessage.getObjectId();
    }

}
