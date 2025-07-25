package org.aldouscloud.aldouscloud.controller;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.response.ObjectVersionResponse;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.ObjectVersion;
import org.aldouscloud.aldouscloud.repository.ObjectEntryRepository;
import org.aldouscloud.aldouscloud.service.ObjectVersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/object-versions")
public class    ObjectVersionController {
    private final ObjectVersionService objectVersionService;
    private final ObjectEntryRepository objectEntryRepository;

    @GetMapping("/{entryId}")
    public ResponseEntity<List<ObjectVersionResponse>> getAllVersions(@PathVariable Long entryId) {
        ObjectEntry entry = objectEntryRepository.findById(entryId)
                .orElseThrow(()-> new RuntimeException("ObjectEntry not found"));
        List<ObjectVersionResponse> versions = objectVersionService.getVersions(entry);
        return ResponseEntity.ok(versions);
    }
}
