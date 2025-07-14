package org.aldouscloud.aldouscloud.service.impl;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.response.AccessKeyResponse;
import org.aldouscloud.aldouscloud.entity.AccessKey;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.exceptions.EntityNotFoundException;
import org.aldouscloud.aldouscloud.mapper.AccessKeyMapper;
import org.aldouscloud.aldouscloud.repository.AccessKeyRepository;
import org.aldouscloud.aldouscloud.repository.UserRepository;
import org.aldouscloud.aldouscloud.service.AccessKeyService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AccessKeyServiceImpl implements AccessKeyService {
    private final AccessKeyRepository accessKeyRepository;
    private final UserRepository userRepository;
    private final SecureRandom random = new SecureRandom();
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public AccessKeyResponse createAccessKey (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User", userId));

        String accessKeyId = generateRandomString(20);
        String secret = generateRandomString(40);
        String secretHash = hashSecret(secret);

        AccessKey accessKey = new AccessKey();
        accessKey.setAccessKeyId(accessKeyId);
        accessKey.setSecretHash(secretHash);
        accessKey.setActive(true);
        accessKey.setUser(user);
        accessKey.setCreatedAt(LocalDateTime.now());

        accessKeyRepository.save(accessKey);

        return AccessKeyMapper.toDto(accessKey,secret);
    }

    private String generateRandomString(int length){
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
    }

    private String hashSecret(String secret){
        return passwordEncoder.encode(secret);
    }
}
