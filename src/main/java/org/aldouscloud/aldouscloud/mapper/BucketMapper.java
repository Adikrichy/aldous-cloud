package org.aldouscloud.aldouscloud.mapper;

import org.aldouscloud.aldouscloud.dto.response.BucketResponse;
import org.aldouscloud.aldouscloud.entity.Bucket;
import org.springframework.stereotype.Component;

@Component
public class BucketMapper {
    public BucketResponse toDto(Bucket bucket){
        if(bucket ==null) return null;
        return BucketResponse.builder()
                .id(bucket.getId())
                .name(bucket.getName())
                .versioningEnabled(bucket.isVersioningEnabled())
                .createdAt(bucket.getCreatedAt())
                .build();
    }
}
