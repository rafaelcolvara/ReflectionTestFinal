import valemobi.http.EncriptaDecriptaAES;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClassMaintainer implements Serializable {
    public static void encryptClass(String inputFile, String outputFile, byte[] expectedDigest) throws  Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile);
        byte[] buffer = new byte[8192];
        int length;
        while ((length = in.read(buffer)) != -1) {
            md.update(buffer, 0, length);
            out.write(buffer, 0, length);
        }
        byte[] digest = md.digest();
        in.close();
        out.close();
        if (!MessageDigest.isEqual(digest, expectedDigest)) {
            throw new Exception("Encrypt do arquivo Java falhou!");
        }
    }
    public static void decryptClass(String inputFile, String outputFile, byte[] expectedDigest) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile);
        byte[] buffer = new byte[8192];
        int length;
        while ((length = in.read(buffer)) != -1) {
            md.update(buffer, 0, length);
            out.write(buffer, 0, length);
        }
        byte[] digest = md.digest();
        in.close();
        out.close();
        if (!MessageDigest.isEqual(digest, expectedDigest)) {
            throw new Exception("Decrypt do arquivo java falhou");
        }
    }

    public static Object getClassFromDeserializedFile(String decryptedFile) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(decryptedFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        fis.close();
        return obj;

    }

    public static byte[] callMethodFromObject(Object deserializedClass, String methodName, byte[]...argument) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> classToCallMethodOn = deserializedClass.getClass();
        Method methodToCall = classToCallMethodOn.getMethod(methodName, byte[].class, byte[].class, byte[].class);
        Object result = methodToCall.invoke(deserializedClass, argument);

        return (byte[])result;
    }

    private static boolean removeTmpFile(String arquivo){
        File file = new File(arquivo);
        return  (file.delete());
    }

    public static byte[] checkDigest(String arquivoOriginal) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        FileInputStream in = new FileInputStream(arquivoOriginal);

        byte[] buffer = new byte[8192];
        int length;
        while ((length = in.read(buffer)) != -1) {
            md.update(buffer, 0, length);
        }
        byte[] originalDigest = md.digest();
        in.close();
        return originalDigest;
    }

    public static void serializeClass( String nomeArquivo) {

        EncriptaDecriptaAES obj = new EncriptaDecriptaAES();

        try {
            FileOutputStream fos = new FileOutputStream(nomeArquivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static Object deserializeFileintoObject(String nomeArquivo) {
        Object obj =null;
        try {
            FileInputStream fis = new FileInputStream(nomeArquivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            fis.close();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
            obj = null;
        } finally {
            return obj;
        }

    }

}
