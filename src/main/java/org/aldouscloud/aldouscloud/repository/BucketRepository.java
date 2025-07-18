package org.aldouscloud.aldouscloud.repository;

import org.aldouscloud.aldouscloud.entity.Bucket;
import org.aldouscloud.aldouscloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<Bucket> findByName(String name);
    List<Bucket> findAllByOwner(User user);
    boolean existsByName(String name);
}
