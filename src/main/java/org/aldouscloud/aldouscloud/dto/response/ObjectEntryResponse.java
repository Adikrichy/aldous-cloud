package org.aldouscloud.aldouscloud.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ObjectEntryResponse {
    private Long id;
    private String objectKey;
    private String path;
    private String bucketName;
    private LocalDateTime uploadedAt;
    private String url;

    public static ObjectEntryResponse from(ObjectEntry objectEntry, String url){
        return ObjectEntryResponse.builder()
                .id(objectEntry.getId())
                .objectKey(objectEntry.getObjectKey())
                .path(objectEntry.getStoragePath())
                .bucketName(objectEntry.getBucket().getName())
                .uploadedAt(objectEntry.getLastModifiedAt())
                .url(url)
                .build();
    }
}
