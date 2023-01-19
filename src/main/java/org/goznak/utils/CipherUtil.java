package org.goznak.utils;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CipherUtil {
    private final Cipher cipher = Cipher.getInstance("AES");
    private final Key key;
    private final String[] keys = {"cW{Qo5L|3T98DQ|I", "8LPAwL%7d@OWK~ha", "nM|Y~*c@o2|Q*xNZ", "i~161pss2D}ilCsx",
            "GnG$mQ9aoDWMi~}3", "S*2hP%atxWA#nYN?", "@zpDHn{KWcR{M}I@", "Hgos?BVhpDIooyqN", "u0m|Pgt%YaItJ7T2",
            "W6$|GWK#D1}Z7iv@"};

    public CipherUtil() throws NoSuchPaddingException, NoSuchAlgorithmException {
        key = new SecretKeySpec(getKey(), "AES");
    }

    public String encryptPass(String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
    public String decryptPass(String encryptedPassword) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(plainText);
    }
    private byte[] getKey(){
        byte[] result = "0000000000000000".getBytes(StandardCharsets.UTF_8);
        for(String key: keys){
            byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            for(int i = 0; i < 16; i++){
                result[i] ^= byteKey[i];
            }
        }
        return result;
    }
}
