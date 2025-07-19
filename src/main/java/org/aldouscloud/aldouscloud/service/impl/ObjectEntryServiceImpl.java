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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        String storagePath =objectStorageManager.save(file,bucketName,originalFilename,user.getId());

        ObjectEntry objectEntry = new ObjectEntry();
        objectEntry.setBucket(bucket);
        objectEntry.setObjectKey(originalFilename);
        objectEntry.setOriginalFilename(file.getOriginalFilename());
        objectEntry.setSize(file.getSize());
        objectEntry.setContentType(file.getContentType());
        objectEntry.setStoragePath(storagePath);
        objectEntry.setDeleted(false);

        return objectEntryRepository.save(objectEntry);

    }
}
