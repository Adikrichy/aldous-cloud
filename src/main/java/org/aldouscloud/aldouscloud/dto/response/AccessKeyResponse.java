package org.aldouscloud.aldouscloud.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AccessKeyResponse {
    private String accessKeyId;
    private String secret;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastUsedAt;
    private Long userId;
}
