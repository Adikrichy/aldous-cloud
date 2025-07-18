package org.aldouscloud.aldouscloud.service.impl;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.request.BucketRequest;
import org.aldouscloud.aldouscloud.dto.response.BucketResponse;
import org.aldouscloud.aldouscloud.entity.Bucket;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.exceptions.BucketExistException;
import org.aldouscloud.aldouscloud.mapper.BucketMapper;
import org.aldouscloud.aldouscloud.repository.BucketRepository;
import org.aldouscloud.aldouscloud.service.BucketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final BucketMapper bucketMapper;

    @Override
    public BucketResponse createBucket(BucketRequest request, User owner){
         if(bucketRepository.existsByName(request.getName())){
             throw new BucketExistException("Bucket already exists");
         }
         Bucket bucket = new Bucket();
         bucket.setName(request.getName());
         bucket.setOwner(owner);
         bucket.setVersioningEnabled(false);
         bucket.setCreatedAt(LocalDateTime.now());

         bucketRepository.save(bucket);
         return bucketMapper.toDto(bucket);
    }

    @Override
    public List<BucketResponse> getAllBuckets(User owner){
        return bucketRepository.findAllByOwner(owner)
                .stream()
                .map(bucketMapper::toDto)
                .toList();
    }

    @Override
    public BucketResponse getBucketByName(String bucketName,User owner){
            Bucket bucket = bucketRepository.findByName(bucketName)
                    .filter(b->b.getOwner().getId().equals(owner.getId()))
                    .orElseThrow(() -> new RuntimeException("Bucket not found or access denied"));
            return bucketMapper.toDto(bucket);

    }
}
