package io.eblock.eos4j.utils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * SHA
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Sha {

	public static final String SHA256 = "SHA-256";

	/**
	 * sha256
	 * 
	 * @param text
	 * @return
	 */
	public static byte[] SHA256(final String text) {
		if (text == null || text.length() == 0) {
			throw new EException("args_empty", "args is empty");
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
			messageDigest.update(text.getBytes("utf8"));
			byte byteBuffer[] = messageDigest.digest();
			return byteBuffer;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * sha256
	 * 
	 * @param text
	 * @return
	 */
	public static byte[] SHA256(final byte[] b) {
		if (b == null || b.length == 0) {
			throw new EException("args_empty", "args is empty");
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
			messageDigest.update(b);
			byte byteBuffer[] = messageDigest.digest();
			return byteBuffer;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HMACSHA256
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] HmacSHA256(byte[] data, byte[] key) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			return mac.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
