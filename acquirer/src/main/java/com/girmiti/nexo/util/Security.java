package com.girmiti.nexo.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.macs.ISO9797Alg3Mac;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Security {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Security.class);
	
	private static String key = Constants.SECURITY_KEY;
	private static String iv = Constants.SECURITY_IV;
	
	public static byte[] StringToByteArray(String str) {
		byte[] val = new byte[str.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			int j = Integer.parseInt(str.substring(index, index + 2), 16);
			val[i] = (byte) j;
		}
		return val;
	}

	public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] encrypt(byte[] plainByte) {

		byte[] cipherByte = null;
		try {
			System.out.println("key len: " + StringToByteArray(key).length);
		      
			DESedeKeySpec dks = new DESedeKeySpec(StringToByteArray(key), 0);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);

			byte[] ivec = StringToByteArray(iv);
			IvParameterSpec encIv = new IvParameterSpec(ivec, 0, ivec.length);
			Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, encIv);
			cipherByte = cipher.doFinal(plainByte);
		} catch (Exception e) {
			LOGGER.error("Error :: Security :: method encrypt unable to process your request ", e);
		}
		return cipherByte;
	}

	public static byte[] decrypt(byte[] cipherByte) {

		byte[] decryptByte = null;
		try {
			DESedeKeySpec dks = new DESedeKeySpec(StringToByteArray(key), 0);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			byte[] ivec = StringToByteArray(iv);
			IvParameterSpec encIv = new IvParameterSpec(ivec, 0, ivec.length);
			Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, securekey, encIv);
			decryptByte = cipher.doFinal(cipherByte);
		} catch (Exception e) {
			LOGGER.error("Error :: Security :: method decrypt unable to process your request ", e);
		}

		return decryptByte;
	}

	public static byte[] getSha256(byte[] messsage) {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(messsage);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}
	
	public static byte[] retailMac(byte[] key, byte[] data) {
		BlockCipher cipher = new DESEngine();
		Mac mac = new ISO9797Alg3Mac(cipher, 64, new ISO7816d4Padding());

		KeyParameter keyP = new KeyParameter(key);
		mac.init(keyP);
		mac.update(data, 0, data.length);

		byte[] out = new byte[8];

		mac.doFinal(out, 0);

		return out;
	}
	
	public static String splitRequest(String request, String start, String end, int i) {
		request = request.substring(request.indexOf(start), request.indexOf(end) + i);
		return request;
	}

	public static byte[] getMACByteArray(String request, String start, String end, int i) {
		String mainRequest = splitRequest(request, start, end, i).replaceAll("\\s+", "");
		mainRequest = mainRequest.replace(splitRequest(mainRequest, "<TxDtTm>", "</TxDtTm>", 9),"");
		byte[] mainBytes= mainRequest.getBytes();
		byte[] mac =  getSha256(mainBytes);
		key = Constants.SECURITY_MAC_KEY;
		mac = retailMac(StringToByteArray(key), mac);
		return mac;
	}
}
