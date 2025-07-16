package org.aldouscloud.aldouscloud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "acess_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false, length = 20)
    private String accessKeyId;

    @Column(nullable = false, length = 100)
    private String secretHash;

    @ManyToOne
    private User user;

    private boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastUsedAt;

    @Transient
    private String plainSecret;
}
