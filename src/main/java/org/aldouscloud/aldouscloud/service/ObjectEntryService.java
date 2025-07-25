package org.aldouscloud.aldouscloud.service;

import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ObjectEntryService {
    ObjectEntry uploadObject(MultipartFile file, String bucketName, User user) throws IOException;
    Resource loadAsResource(String bucketName, String objectKey) throws IOException;
    String generateObjectUrl(String bucketName, String objectKey);
}
