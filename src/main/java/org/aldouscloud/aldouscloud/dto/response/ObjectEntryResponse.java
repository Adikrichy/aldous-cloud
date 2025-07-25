package org.aldouscloud.aldouscloud.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ObjectEntryResponse {
    private Long id;
    private String objectKey;
    private long size;
    private String originalFilename;
    private String path;
    private String contentType;
    private String etag;
    private String bucketName;
    private LocalDateTime uploadedAt;
    private String url;
    private List<ObjectVersionResponse> versions;

    public static ObjectEntryResponse from(ObjectEntry objectEntry, String url){
        return ObjectEntryResponse.builder()
                .id(objectEntry.getId())
                .objectKey(objectEntry.getObjectKey())
                .size(objectEntry.getSize())
                .originalFilename(objectEntry.getOriginalFilename())
                .path(objectEntry.getStoragePath())
                .contentType(objectEntry.getContentType())
                .etag(objectEntry.getEtag())
                .bucketName(objectEntry.getBucket().getName())
                .uploadedAt(objectEntry.getLastModifiedAt())
                .url(url)
                .versions(objectEntry.getVersionId()
                        .stream()
                        .map(ObjectVersionResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
