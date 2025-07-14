package org.aldouscloud.aldouscloud.repository;

import org.aldouscloud.aldouscloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
