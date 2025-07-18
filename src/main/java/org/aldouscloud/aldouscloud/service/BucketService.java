package org.aldouscloud.aldouscloud.service;

import org.aldouscloud.aldouscloud.dto.request.BucketRequest;
import org.aldouscloud.aldouscloud.dto.response.BucketResponse;
import org.aldouscloud.aldouscloud.entity.User;

import java.util.List;

public interface BucketService {
    BucketResponse createBucket(BucketRequest request, User owner);
    List<BucketResponse> getAllBuckets(User owner);
    BucketResponse getBucketByName(String bucketName, User owner);
}
