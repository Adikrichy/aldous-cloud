package org.aldouscloud.aldouscloud.controller;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.response.ObjectEntryResponse;
import org.aldouscloud.aldouscloud.entity.ObjectEntry;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.service.AuthService;
import org.aldouscloud.aldouscloud.service.ObjectEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buckets/{bucketName}/objects")
public class ObjectEntryController {
    private final ObjectEntryService objectEntryService;
    private final AuthService authService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ObjectEntryResponse> uploadObject(
            @PathVariable String bucketName,
            @RequestParam("file")MultipartFile file
            ) throws IOException{
        User currentUser = authService.getCurrentUser();
        ObjectEntry entry = objectEntryService.uploadObject(file, bucketName, currentUser);
        return ResponseEntity.ok(ObjectEntryResponse.from(entry));
    }
}
