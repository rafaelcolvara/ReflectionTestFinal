import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class ReflectionApplication {


    static String originalFile = "EncriptaDecriptaAES.ser";

    static String classeDeserializada = "EncriptaDecriptaAES.des";
    static String encryptedFile = "java.bin";
    static String tempDecryptedFile = "temp.out";

    static Object obj = null;
    private static final int KEY_LENGTH = 128;
    private static final int IV_LENGTH = 16;

    static byte[] key = new byte[KEY_LENGTH / 8];
    static byte[] iv = new byte[IV_LENGTH];
    public static void main(String[] args) throws Exception {

        String message = "Olá Mundo";

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(key);
        secureRandom.nextBytes(iv);

        byte[] encryptedMessage = EncriptaDecriptaAES.encrypt(message.getBytes(), key, iv);
        byte[] decryptedMessage = EncriptaDecriptaAES.decrypt(encryptedMessage, key, iv);

        System.out.println("Mensagem Original: " + message);
        System.out.println("Message Encryptada: " + Base64.getEncoder().encodeToString(encryptedMessage));
        System.out.println("Mensagem Decryptada: " + new String(decryptedMessage));

        System.out.println("************************ " );
        System.out.println("************************ " );

        byte[] encryptedMessageReflection = callMethodThroughReflection("encrypt", message.getBytes(), key, iv);
        byte[] decryptedMessageReflection = callMethodThroughReflection("decrypt", encryptedMessageReflection, key, iv);

        System.out.println("Mensagem Decryptada via Reflection: " + new String(decryptedMessageReflection));



    }



    private static void SerializaClasseEmArquivo() {
        ClassMaintainer.serializeClass(originalFile);
        System.out.println("Serializou o arquivo: " + originalFile);
    }


    static void DeserializaEmArquivo() {
		ClassMaintainer.serializeClass(originalFile);
    }


    static void DeserializeFileIntoObject() {
        obj = ClassMaintainer.deserializeFileintoObject(originalFile);

    }

    static void EncryptJavaFile() throws Exception {
		ClassMaintainer.encryptClass(originalFile, encryptedFile, ClassMaintainer.checkDigest(originalFile));
    }

    static void DecryptJavaFile() throws Exception {
		ClassMaintainer.decryptClass(encryptedFile, tempDecryptedFile, ClassMaintainer.checkDigest(originalFile));
    }

    static void GetClassFromDeserializedFile() {
        obj = ClassMaintainer.deserializeFileintoObject(tempDecryptedFile);
    }

    static byte[] callMethodThroughReflection(String method, byte[]...argument) {
        try {
            obj = ClassMaintainer.getClassFromDeserializedFile(originalFile);
            return ClassMaintainer.callMethodFromObject( obj, method, argument);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

