package valemobi.http;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

public class EncriptaDecriptaAES implements Serializable {

    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 128;
    private static final int IV_LENGTH = 16;


    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(data);
    }




}
