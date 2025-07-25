package org.aldouscloud.aldouscloud.service.impl;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.response.ObjectVersionResponse;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.ObjectVersion;
import org.aldouscloud.aldouscloud.repository.ObjectVersionRepository;
import org.aldouscloud.aldouscloud.service.ObjectVersionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjectVersionServiceImpl implements ObjectVersionService {
    private final ObjectVersionRepository objectVersionRepository;

    @Override
    public void saveVersion(ObjectEntry entry){
        ObjectVersion version = new ObjectVersion();
        version.setEntry(entry);
        version.setVersionId("v"+ UUID.randomUUID().toString());
        version.setEtag("");
        version.setSize(entry.getSize());
        version.setUploadedAt(LocalDateTime.now());

        objectVersionRepository.save(version);
    }

    @Override
    public List<ObjectVersionResponse> getVersions(ObjectEntry entry) {
        return objectVersionRepository.findAllByEntry(entry).stream()
                .map(ObjectVersionResponse::from)
                .toList();
    }
}
