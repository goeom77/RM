package org.gyu.solution.global.base;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.springframework.core.env.Environment;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
public class Encryptor {
    private final Environment env;
    private static final String ALGORITHM = "AES";
// env.getProperty("ENCRYPT.ENCRYPT_SECRET_KEY",String.class);
    public String encrypt(String value) {
        try{
            SecretKeySpec keySpec =
                    new SecretKeySpec(Objects.requireNonNull(env.getProperty(
                            "ENCRYPT.ENCRYPT_SECRET_KEY"
                            , String.class)
                    ).getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
            // todo: 에러 처리
        }
    }
    public String decrypt(String value) {
        try{
            SecretKeySpec keySpec =
                    new SecretKeySpec(Objects.requireNonNull(env.getProperty(
                            "ENCRYPT.ENCRYPT_SECRET_KEY"
                            , String.class)
                    ).getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decrypted);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
            // todo: 에러 처리
        }
    }
}
