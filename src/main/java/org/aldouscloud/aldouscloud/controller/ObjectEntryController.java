package org.aldouscloud.aldouscloud.controller;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.response.ObjectEntryResponse;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.service.AuthService;
import org.aldouscloud.aldouscloud.service.ObjectEntryService;
import org.aldouscloud.aldouscloud.service.ObjectVersionService;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buckets/{bucketName}/objects")
public class ObjectEntryController {
    private final ObjectEntryService objectEntryService;
    private final AuthService authService;
    private final ObjectVersionService objectVersionService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ObjectEntryResponse> uploadObject(
            @PathVariable String bucketName,
            @RequestParam("file")MultipartFile file
            ) throws IOException{
        User currentUser = authService.getCurrentUser();
        ObjectEntry entry = objectEntryService.uploadObject(file, bucketName, currentUser);
        objectVersionService.saveVersion(entry);
        String url = objectEntryService.generateObjectUrl(bucketName,entry.getObjectKey());
        return ResponseEntity.ok(ObjectEntryResponse.from(entry, url));
    }

    @GetMapping("/region/{objectKey}")
    public ResponseEntity<Resource> getObject(
            @PathVariable String bucketName,
            @PathVariable String objectKey) throws IOException {
        Resource resource = objectEntryService.loadAsResource(bucketName, objectKey);
        String contentType = Files.probeContentType(resource.getFile().toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE,contentType != null ? contentType : "application/octet-stream")
                .body(resource);
    }
}
