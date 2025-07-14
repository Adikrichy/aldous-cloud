package org.aldouscloud.aldouscloud.service;

import org.aldouscloud.aldouscloud.dto.response.AccessKeyResponse;

public interface AccessKeyService {
    AccessKeyResponse createAccessKey (Long userId);
}
