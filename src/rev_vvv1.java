import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Arrays;

public class rev_vvv1 {

    public static void main(String[] args) {
        String folderPath = "C:\\Code\\JAVA\\vvv1\\src\\files";
        String keyFilePath = folderPath + "\\thekey.key";

        try {
            String key = new String(Files.readAllBytes(Paths.get(keyFilePath))).trim();

            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".enc")) {
                    try {
                        byte[] encryptedData = Files.readAllBytes(file.toPath());
                        byte[] decryptedData = decrypt(encryptedData, key);

                        String originalFileName = file.getAbsolutePath().replaceFirst("\\.enc$", "");
                        Files.write(Paths.get(originalFileName), decryptedData);
                        Files.delete(file.toPath());

                        System.out.println("Decrypted " + file.getName());
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read key from: " + keyFilePath);
        }
    }

    public static SecretKeySpec getAESKeyFromString(String myKey) throws Exception {
        byte[] key = myKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        return new SecretKeySpec(Arrays.copyOf(key, 16), "AES");
    }

    public static byte[] decrypt(byte[] data, String keyString) throws Exception {
        SecretKeySpec secretKey = getAESKeyFromString(keyString);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = Arrays.copyOfRange(data, 0, 16);
        byte[] encrypted = Arrays.copyOfRange(data, 16, data.length);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(encrypted);
    }
}