package org.aldouscloud.aldouscloud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "object_version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ObjectEntry entry;

    private String versionId;
    private String etag;
    private long size;
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
