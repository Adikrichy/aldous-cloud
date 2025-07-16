package org.aldouscloud.aldouscloud.security.signaturev4;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aldouscloud.aldouscloud.entity.AccessKey;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Component
@Slf4j
public class SignatureValidator {
    public boolean isValidSignature(HttpServletRequest request,
                                    AccessKey accessKey,
                                    String providedSignature)
    {
        try{
            String canonicalRequest = buildCanonicalRequest(request);
            String plainSecret = accessKey.getPlainSecret();
            if(plainSecret ==null || plainSecret.isEmpty()){
                log.warn("Missing plainSecret in AccessKey — cannot validate signature.");
                return false;
            }
            String computedSignature = hmacSHA256(canonicalRequest, plainSecret);
            return computedSignature.equals(providedSignature);
        } catch(Exception e){
            log.error("Signature validation failed",e);
        }
        return false;
    }

    private String buildCanonicalRequest(HttpServletRequest request){
        String method = request.getMethod();
        String path = request.getRequestURI();

        String date = Optional.ofNullable(request.getHeader("X-Amz-Date")).orElse("");
        return method + "\n" + path + "\n" + date;
    }
    private String hmacSHA256(String data, String secretKey) throws Exception{
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(rawHmac);
    }
}
