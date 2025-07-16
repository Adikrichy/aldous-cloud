package org.aldouscloud.aldouscloud.mapper;

import org.aldouscloud.aldouscloud.dto.request.AccessKeyCreateRequest;
import org.aldouscloud.aldouscloud.dto.response.AccessKeyResponse;
import org.aldouscloud.aldouscloud.entity.AccessKey;
import org.aldouscloud.aldouscloud.entity.User;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class AccessKeyMapper {
    //Entity to DTO (response)
    public static AccessKeyResponse toDto(AccessKey entity, String plainSecret){
        if(entity == null) return null;

        AccessKeyResponse dto = new AccessKeyResponse();
        dto.setAccessKeyId(entity.getAccessKeyId());
        // секрет (plain) отдаём ТОЛЬКО при includeSecret = true
        dto.setSecret(plainSecret);
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastUsedAt(entity.getLastUsedAt());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        return dto;
    }
    //DTO to Entity
    public static AccessKey toEntity(AccessKeyCreateRequest dto, User user, String accessKeyId, String secret){
        AccessKey entity = new AccessKey();
        entity.setUser(user);
        entity.setAccessKeyId(accessKeyId);
        entity.setSecretHash(secret);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setActive(true);
        return entity;
    }
}
