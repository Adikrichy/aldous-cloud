package org.aldouscloud.aldouscloud.repository;

import org.aldouscloud.aldouscloud.entity.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {
    Optional<AccessKey> findByAccessKeyId (String accessKeyId);
}
