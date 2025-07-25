package org.aldouscloud.aldouscloud.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aldouscloud.aldouscloud.entity.ObjectVersion;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ObjectVersionResponse {
    private String versionId;
    private String etag;
    private long size;
    private LocalDateTime uploadedAt;

    public static ObjectVersionResponse from(ObjectVersion objectVersion) {
        return ObjectVersionResponse.builder()
                .versionId(objectVersion.getVersionId())
                .etag(objectVersion.getEtag())
                .size(objectVersion.getSize())
                .uploadedAt(objectVersion.getUploadedAt())
                .build();
    }
}
