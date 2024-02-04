package org.gyu.solution.global.base;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.springframework.core.env.Environment;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
public class Encryptor {
    private final Environment env;
    private static final String ALGORITHM = "AES";

public String encryptTokens(String value1, String value2) {
    try {
        String combinedValue = value1 + "-" + value2; // 예시에서는 "-"를 구분자로 사용
        SecretKeySpec keySpec =
                new SecretKeySpec(Objects.requireNonNull(env.getProperty(
                        "ENCRYPT.ENCRYPT_SECRET_KEY"
                        , String.class)
                ).getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(combinedValue.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
        throw new BusinessException(ErrorCode.ENCRYPT_ERROR);
    }
}

    public String[] decryptTokens(String encryptedToken) {
        try {
            SecretKeySpec keySpec =
                    new SecretKeySpec(Objects.requireNonNull(env.getProperty(
                            "ENCRYPT.ENCRYPT_SECRET_KEY"
                            , String.class)
                    ).getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedToken));
            String combinedValue = new String(decrypted);
            return combinedValue.split("-"); // 예시에서는 "-"를 구분자로 사용
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.ENCRYPT_ERROR);
        }
    }
}
