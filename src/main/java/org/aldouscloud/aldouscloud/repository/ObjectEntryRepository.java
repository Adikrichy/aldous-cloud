package org.aldouscloud.aldouscloud.repository;

import org.aldouscloud.aldouscloud.entity.Bucket;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectEntryRepository extends JpaRepository<ObjectEntry, Long> {
    boolean existsByBucketAndObjectKey(Bucket bucket, String objectKey);
    Optional<ObjectEntry> findByBucketAndObjectKey(Bucket bucket, String objectKey);
}
