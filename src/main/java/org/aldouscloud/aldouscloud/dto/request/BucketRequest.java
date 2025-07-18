package org.aldouscloud.aldouscloud.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketRequest {
    @NotBlank(message = "Bucket name must be not blank")
    private String name;
}
