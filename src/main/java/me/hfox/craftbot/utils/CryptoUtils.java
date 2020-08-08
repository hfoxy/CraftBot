package me.hfox.craftbot.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.hfox.craftbot.exception.connection.BotEncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class CryptoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

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

    public static String getServerIdHash(String serverId, SecretKey secretKey, PublicKey publicKey) throws BotEncryptionException {
        try {
            byte[] digest = digest(serverId.getBytes(StandardCharsets.UTF_8), secretKey.getEncoded(), publicKey.getEncoded());
            return new BigInteger(digest).toString(16);
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

    /*public static String getServerIdHash(String serverId) throws BotEncryptionException {
        try {
            byte[] digest = digest(serverId.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(digest).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            throw new BotEncryptionException("Unable to generate hash", ex);
        }
    }

    private static byte[] digest(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return md.digest(data);
    }*/

    public static Cipher createNetworkCipher(int operationMode, Key key) throws BotEncryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(operationMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (GeneralSecurityException ex) {
            throw new BotEncryptionException("Unable to create Cipher", ex);
        }
    }

    public static Cipher createCipher(int operationMode, String algorithm, Key key) throws BotEncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(operationMode, key);
            return cipher;
        } catch (GeneralSecurityException ex) {
            throw new BotEncryptionException("Unable to create Cipher", ex);
        }
    }

    public static byte[] encryptData(Key key, byte[] data) throws BotEncryptionException {
        return performCipherOperation(1, key, data);
    }

    public static byte[] decryptData(Key key, byte[] data) throws BotEncryptionException {
        return performCipherOperation(2, key, data);
    }

    private static byte[] performCipherOperation(int opMode, Key key, byte[] data) throws BotEncryptionException {
        try {
            return createCipher(opMode, key.getAlgorithm(), key).doFinal(data);
        } catch (BadPaddingException | IllegalBlockSizeException ex) {
            throw new BotEncryptionException("Unable to perform Cipher operation", ex);
        }
    }

    public static ByteBuf decipher(Cipher cipher, ByteBuf buffer) throws ShortBufferException {
        int length = buffer.readableBytes();
        byte[] data = new byte[length];
        buffer.readBytes(data);

        ByteBuf buf = Unpooled.buffer(cipher.getOutputSize(length));
        buf.writerIndex(cipher.update(data, 0, length, buf.array(), buf.arrayOffset()));
        return buf;
    }

    public static void cipher(Cipher cipher, ByteBuf in, ByteBuf out) throws ShortBufferException {
        int length = in.readableBytes();
        byte[] data = new byte[length];
        in.readBytes(data);

        int outputLength = cipher.getOutputSize(length);
        byte[] output = new byte[outputLength];

        int newLength = cipher.update(data, 0, length, output);
        out.writeBytes(output, 0, newLength);
    }

}
