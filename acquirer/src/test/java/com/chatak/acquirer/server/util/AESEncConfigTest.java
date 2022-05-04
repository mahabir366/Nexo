package com.chatak.acquirer.server.util;

import java.security.InvalidAlgorithmParameterException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.girmiti.nexo.util.AESEncConfig;


@RunWith(MockitoJUnitRunner.class)
public class AESEncConfigTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AESEncConfigTest.class);

	
	public static final String STR_TO_ENCRYPT = "strToEncrypt";

	public static final String STR_TO_DECRYPT = "abcdefghijklmnopqrstuv";

	@Test
	public void testencrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
			String strToEncrypt = STR_TO_ENCRYPT;
			Assert.assertNotNull(AESEncConfig.encrypt(strToEncrypt));
}

	@Test
	public void testdecrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String strToDecrypt = STR_TO_DECRYPT;
		Assert.assertNotNull(AESEncConfig.decrypt(strToDecrypt));
	}

}