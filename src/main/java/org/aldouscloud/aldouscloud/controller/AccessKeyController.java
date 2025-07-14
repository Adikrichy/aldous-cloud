package org.aldouscloud.aldouscloud.controller;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.request.AccessKeyCreateRequest;
import org.aldouscloud.aldouscloud.dto.response.AccessKeyResponse;
import org.aldouscloud.aldouscloud.service.AccessKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/acess-keys")
public class AccessKeyController {
    private final AccessKeyService accessKeyService;

    @PostMapping
    public ResponseEntity<AccessKeyResponse> createAccessKey(@RequestBody AccessKeyCreateRequest request){
        AccessKeyResponse response  = accessKeyService.createAccessKey(request.getUserId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
