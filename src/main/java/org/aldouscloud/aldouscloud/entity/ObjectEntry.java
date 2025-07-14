package org.aldouscloud.aldouscloud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "object_entries",
       indexes = @Index(columnList = "bucket_id, objectKey", unique = true))
public class ObjectEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bucket bucket;

    @Column(nullable = false)
    private String objectKey;

    @Column(nullable = false)
    private long size;

    private String contentType;
    private String etag;

    private LocalDateTime lastModifiedAt;
    private boolean deleted = false;

    @PrePersist
    void onCreate(){lastModifiedAt = LocalDateTime.now();}
}
