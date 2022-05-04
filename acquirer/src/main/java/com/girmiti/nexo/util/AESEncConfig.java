package com.girmiti.nexo.util;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

public class AESEncConfig {
	private AESEncConfig() {

	}

    private static final String HASH_ALGO = "SHA-256";
	
	private static final String AES_ALGO = "AES/CBC/PKCS5Padding";
	
	

	public static String encrypt(String strToEncrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		// Generating IV.
		int ivSize =16;
		byte[] iv = new byte[ivSize];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		// Hashing key.
		MessageDigest digest = MessageDigest.getInstance(HASH_ALGO);
		digest.update("YqPeabr952yAbEHPXTenDTlNmimBUiba".getBytes(StandardCharsets.UTF_8));
		byte[] keyBytes = new byte[16];
		System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

		// Encrypt.
		Cipher cipher = Cipher.getInstance(AES_ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));

		// Combine IV and encrypted part.
		byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
		System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
		System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);
		return Base64.getEncoder().encodeToString(encryptedIVAndText);
	}
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException,
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		System.out.println(decrypt("/r0eK/rLLrDuHGvU/loj1FYllfAla5eZjuSUZVlmIbgCbZBjMxv2Bo9Hf8aFCJtGuhODqqz997PyuwXMfCQCg/h5A7TiiVWbgejyN5dS3E/9THX69JXhwnikte5u7nyTB5MfzZqSca0M6qz+RsDH3A=="));
	}
	public static String decrypt(String strToDecrypt) throws NoSuchAlgorithmException, NoSuchPaddingException,
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		byte[] encrypted = Base64.getDecoder().decode(strToDecrypt);

		int ivSize = 16;
		int keySize = 16;

		// Extract IV.
		byte[] iv = new byte[ivSize];
		System.arraycopy(encrypted, 0, iv, 0, iv.length);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		// Extract encrypted part.
		int encryptedSize = encrypted.length - ivSize;
		byte[] encryptedBytes = new byte[encryptedSize];
		System.arraycopy(encrypted, ivSize, encryptedBytes, 0, encryptedSize);

		// Hash key.
		byte[] keyBytes = new byte[keySize];
		MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
		md.update("YqPeabr952yAbEHPXTenDTlNmimBUiba".getBytes(StandardCharsets.UTF_8));
		System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

		// Decrypt.
		Cipher cipherDecrypt = Cipher.getInstance(AES_ALGO);
		cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

		return new String(decrypted);
	}

}
