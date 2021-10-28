package com.todo.todolist;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author docente
 */
public class EsempiCrittografia {

    private static final String SYM_ALGORITHM = "AES";
    private static final Integer SYM_KEY_SIZE = 256;
    private static final String ASYM_ALGORITHM = "RSA";
    private static final Integer ASYM_KEY_SIZE = 4096;

    private static final String TEXT = "To be or not to be that is the question";

    // Generazione chiave simmetrica
    public static Key generateSymmetricKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(SYM_ALGORITHM);
        generator.init(SYM_KEY_SIZE);
        SecretKey key = generator.generateKey();
        return key;
    }

    // Generazione chiave asimmetrica
    public static KeyPair generateAsymmetricKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ASYM_ALGORITHM);
        generator.initialize(ASYM_KEY_SIZE);
        long start = System.currentTimeMillis();
        KeyPair keyPair = generator.generateKeyPair();
        System.out.println("Tempo generazione chiave RSA: " + (System.currentTimeMillis() - start));
        return keyPair;
    }

    // Crittazione simmetrica
    public static byte[] encrypt(Key key, byte[] iv, byte[] plaintext) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(plaintext);
    }

    // Decrittazione simmetrica
    public static byte[] decrypt(Key key, byte[] iv, byte[] ciphertext) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }

    // Generazione vettore di inizializzazione
    public static byte[] generateInitVector() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[SYM_KEY_SIZE / 16];
        random.nextBytes(iv);
        return iv;
    }

    // Crittazione asimmetrica
    public static byte[] encryptAsym(PublicKey key, byte[] plaintext) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    // Decrittazione asimmetrica
    public static byte[] decryptAsym(PrivateKey key, byte[] ciphertext) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

    // Hash
    public static byte[] hash(byte[] input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);

        byte[] output = md.digest(input);

        return output;
    }

    // Encode md5 to string
    public static String toHex(byte[] input) {
        StringBuilder sb = new StringBuilder();

        for (byte b : input) {
            sb.append(Integer.toHexString(0xFF & b));
        }

        return sb.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            Base64.Decoder decoder = Base64.getDecoder();

            // Generazione chiavi
            Key myKey = generateSymmetricKey();
            byte[] iv = generateInitVector();

            KeyPair myKeyPair = generateAsymmetricKey();

            System.out.println("Chiave AES 256 bit: " + Base64.getEncoder().encodeToString(myKey.getEncoded()));
            System.out.println("Chiave pubblica RSA 4096: " + Base64.getEncoder().encodeToString(myKeyPair.getPublic().getEncoded()));
            System.out.println("Chiave privata RSA 4096: " + Base64.getEncoder().encodeToString(myKeyPair.getPrivate().getEncoded()));
            // Stampo la chiave come numero
            System.out.println("Chiave privata RSA 4096 come numero: " +new BigInteger(myKeyPair.getPublic().getEncoded()));

            // Crittazione simmetrica
            String cipherText = encoder.encodeToString(encrypt(myKey, iv, TEXT.getBytes()));
            System.out.println("Testo crittato con AES: " + cipherText);
            String plainText = new String(decrypt(myKey, iv, decoder.decode(cipherText)));
            System.out.println("Testo decriptato con AES: " + plainText);

            // Crittazione asimmetrica - base64
            cipherText = encoder.encodeToString(encryptAsym(myKeyPair.getPublic(), TEXT.getBytes()));
            System.out.println("Testo crittato con RSA: " + cipherText);
            plainText = new String(decryptAsym(myKeyPair.getPrivate(), decoder.decode(cipherText)));
            System.out.println("Testo decriptato con RSA: " + plainText);

            // Crittazione asimmetrica - BigInteger
            BigInteger bi = new BigInteger(encryptAsym(myKeyPair.getPublic(), TEXT.getBytes()));
            System.out.println("Testo crittato con RSA come numero: " + bi);
            plainText = new String(decryptAsym(myKeyPair.getPrivate(), bi.toByteArray()));
            System.out.println("Testo decriptato con RSA come numero: " + plainText);

            // Hash
            System.out.println("Hash MD5 del testo: " + toHex(hash(TEXT.getBytes(), "MD5")));
            System.out.println("Hash SHA-1 del testo: " + toHex(hash(TEXT.getBytes(), "SHA-1")));
            System.out.println("Hash SHA-256 del testo: " + toHex(hash(TEXT.getBytes(), "SHA-256")));

            // Passo un file letto come array di byte alla funzione di hash
            byte[] bytes = Files.readAllBytes(Paths.get("/tmp/file.txt"));
            System.out.println("Hash MD5 del testo: " + toHex(hash((new String(bytes)).trim().getBytes(), "MD5")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}