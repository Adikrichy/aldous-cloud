package org.aldouscloud.aldouscloud.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileSystemStorageManager implements ObjectStorageManager {

    @Value("${storage.path}")
    private String storageRootPath;  // Пример: "/opt/aldouscloud/storage"

    @Override
    public String save(MultipartFile file,String bucketName, String objectKey, Long userId) throws IOException{
        // Сначала sanitize имена
        String sanitizedFileName = StringUtils.cleanPath(objectKey);

        Path userBucketPath = Paths.get(storageRootPath, String.valueOf(userId), bucketName);
        Files.createDirectories(userBucketPath); // создаёт если не существует

        Path targetPath = userBucketPath.resolve(sanitizedFileName);
        // Перезапись если существует (в будущем можно добавить версии)
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return targetPath.toAbsolutePath().toString();
    }
}

