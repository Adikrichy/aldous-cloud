package org.aldouscloud.aldouscloud.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketResponse {
    private Long id;
    private String name;
    private boolean versioningEnabled;
    private LocalDateTime createdAt;
}
