package org.aldouscloud.aldouscloud.service;

import org.aldouscloud.aldouscloud.dto.response.ObjectVersionResponse;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;

import java.util.List;

public interface ObjectVersionService {
    void saveVersion(ObjectEntry entry);
    List<ObjectVersionResponse> getVersions(ObjectEntry entry);
}
