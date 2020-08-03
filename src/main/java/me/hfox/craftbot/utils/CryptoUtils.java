package me.hfox.craftbot.utils;

import me.hfox.craftbot.exception.connection.BotEncryptionException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class CryptoUtils {

    public static SecretKey generateSecretKey() throws BotEncryptionException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new BotEncryptionException("Unable to generate secret key", ex);
        }
    }

    public static PublicKey decodePublicKey(byte[] key) throws BotEncryptionException {
        try {
            EncodedKeySpec spec = new X509EncodedKeySpec(key);
            KeyFactory rsaFactory = KeyFactory.getInstance("RSA");
            return rsaFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException ex) {
            throw new BotEncryptionException("No RSA algorithm", ex);
        } catch (InvalidKeySpecException ex) {
            throw new BotEncryptionException("Invalid key specification", ex);
        }
    }

    public static String getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) throws BotEncryptionException {
        try {
            byte[] digest = digest(serverId.getBytes("ISO_8859_1"), publicKey.getEncoded(), secretKey.getEncoded());
            return new BigInteger(digest).toString(16);
        } catch (UnsupportedEncodingException ex) {
            throw new BotEncryptionException("Unsupported text encoding", ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new BotEncryptionException("Unable to generate hash", ex);
        }
    }

    private static byte[] digest(byte[]... data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        for (byte[] bytes : data) {
            md.update(bytes);
        }

        return md.digest();
    }

    public static Cipher createCipher(int operationMode, Key key) throws BotEncryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(operationMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (GeneralSecurityException ex) {
            throw new BotEncryptionException("Unable to create Cipher", ex);
        }
    }

}
