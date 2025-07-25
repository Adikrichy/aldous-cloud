package org.aldouscloud.aldouscloud.repository;

import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.ObjectVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ObjectVersionRepository extends JpaRepository<ObjectVersion, Long> {
    List<ObjectVersion> findAllByEntry(ObjectEntry entry);
}
