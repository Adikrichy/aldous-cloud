package org.aldouscloud.aldouscloud.service.impl;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.entity.Bucket;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.exceptions.EntityNotFoundException;
import org.aldouscloud.aldouscloud.file.ObjectStorageManager;
import org.aldouscloud.aldouscloud.repository.BucketRepository;
import org.aldouscloud.aldouscloud.repository.ObjectEntryRepository;
import org.aldouscloud.aldouscloud.service.ObjectEntryService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ObjectEntryServiceImpl implements ObjectEntryService {
    private final BucketRepository bucketRepository;
    private final ObjectEntryRepository objectEntryRepository;
    private final ObjectStorageManager objectStorageManager;

    @Override
    @Transactional
    public ObjectEntry uploadObject(MultipartFile file, String bucketName, User user) throws IOException{

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || originalFilename.isBlank()){
            throw new IllegalArgumentException("File name must not be empty");
        }
        Bucket bucket = bucketRepository.findByNameAndOwner(bucketName,user)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Bucket not found", "Name = " + bucketName + ", UserId = " + user.getId()
                ));


        if(objectEntryRepository.existsByBucketAndObjectKey(bucket,originalFilename)){
            throw new IllegalArgumentException("Object with name '" + originalFilename + "' already exists in this bucket");
        }
        String objectKey = originalFilename;
        String absolutePath = objectStorageManager.save(file,bucketName,objectKey);

        ObjectEntry objectEntry = new ObjectEntry();
        objectEntry.setBucket(bucket);
        objectEntry.setObjectKey(objectKey); //Поменяли
        objectEntry.setOriginalFilename(originalFilename);
        objectEntry.setSize(file.getSize());
        objectEntry.setContentType(file.getContentType());
        objectEntry.setStoragePath(absolutePath);
        objectEntry.setDeleted(false);

        return objectEntryRepository.save(objectEntry);

    }

    @Override
    public Resource loadAsResource(String bucketName, String objectKey) throws IOException {
         Path filePath = Paths.get("C:/Users/adile/OneDrive/Рабочий стол/Aldousrich/Cloud/aldous-cloud/.opt/aldous-cloud/storage")
                 .resolve(bucketName)
                 .resolve(objectKey)
                 .normalize();
         Resource resource = new UrlResource(filePath.toUri());

         if(!resource.exists() || !resource.isReadable()){
             throw new FileNotFoundException("File not found: " + objectKey);
         }
         return resource;
    }

    @Override
    public String generateObjectUrl(String bucketName, String objectKey){
        return String.format("http://localhost:8080/api/buckets/%s/objects/region/%s", bucketName, objectKey);
    }
}
