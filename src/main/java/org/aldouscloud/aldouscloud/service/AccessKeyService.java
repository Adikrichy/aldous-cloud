package org.aldouscloud.aldouscloud.service;

import org.aldouscloud.aldouscloud.dto.response.AccessKeyResponse;
import org.aldouscloud.aldouscloud.entity.AccessKey;

import java.util.Optional;

public interface AccessKeyService {
    AccessKeyResponse createAccessKey (Long userId);
    Optional<AccessKey> findAccessKeyById(String accessKeyId);
    void updateLastUsedAt(Long accessKeyId);
}
