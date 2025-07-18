package org.aldouscloud.aldouscloud.controller;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.request.BucketRequest;
import org.aldouscloud.aldouscloud.dto.response.BucketResponse;
import org.aldouscloud.aldouscloud.entity.Bucket;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bucket")
@RequiredArgsConstructor
public class BucketController {
    private final BucketService bucketService;

    @PostMapping("/create")
    public ResponseEntity<BucketResponse> create(@RequestBody BucketRequest request,@AuthenticationPrincipal User owner){
        BucketResponse bucketResponse = bucketService.createBucket(request, owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(bucketResponse);
    }

    @GetMapping
    public ResponseEntity<List<BucketResponse>> getAllBuckets(@AuthenticationPrincipal User owner){
        return ResponseEntity.ok(bucketService.getAllBuckets(owner));
    }

    @GetMapping("/{name}")
    public ResponseEntity<BucketResponse> getBucketByName(@RequestParam("name") String name,@AuthenticationPrincipal User owner){
        return ResponseEntity.ok(bucketService.getBucketByName(name,owner));
    }
}
