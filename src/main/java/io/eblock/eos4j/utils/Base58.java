package io.eblock.eos4j.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Base58 {

	public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();

	private static final int[] INDEXES = new int[128];

	static {
		for (int i = 0; i < INDEXES.length; i++) {
			INDEXES[i] = -1;
		}
		for (int i = 0; i < ALPHABET.length; i++) {
			INDEXES[ALPHABET[i]] = i;
		}
	}

	/**
	 * encode
	 * 
	 * @param input
	 * @return
	 */
	public static String encode(byte[] input) {
		if (input.length == 0) {
			return "";
		}
		input = copyOfRange(input, 0, input.length);
		int zeroCount = 0;
		while (zeroCount < input.length && input[zeroCount] == 0) {
			++zeroCount;
		}
		byte[] temp = new byte[input.length * 2];
		int j = temp.length;
		int startAt = zeroCount;
		while (startAt < input.length) {
			byte mod = divmod58(input, startAt);
			if (input[startAt] == 0) {
				++startAt;
			}
			temp[--j] = (byte) ALPHABET[mod];
		}
		while (j < temp.length && temp[j] == ALPHABET[0]) {
			++j;
		}
		while (--zeroCount >= 0) {
			temp[--j] = (byte) ALPHABET[0];
		}
		byte[] output = copyOfRange(temp, j, temp.length);
		try {
			return new String(output, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e); // Cannot happen.
		}
	}

	/**
	 * decode
	 * 
	 * @param input
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static byte[] decode(String input) throws IllegalArgumentException {
		if (input.length() == 0) {
			return new byte[0];
		}
		byte[] input58 = new byte[input.length()];
		for (int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			int digit58 = -1;
			if (c >= 0 && c < 128) {
				digit58 = INDEXES[c];
			}
			if (digit58 < 0) {
				throw new IllegalArgumentException("Illegal character " + c + " at " + i);
			}
			input58[i] = (byte) digit58;
		}
		int zeroCount = 0;
		while (zeroCount < input58.length && input58[zeroCount] == 0) {
			++zeroCount;
		}
		byte[] temp = new byte[input.length()];
		int j = temp.length;
		int startAt = zeroCount;
		while (startAt < input58.length) {
			byte mod = divmod256(input58, startAt);
			if (input58[startAt] == 0) {
				++startAt;
			}
			temp[--j] = mod;
		}
		while (j < temp.length && temp[j] == 0) {
			++j;
		}
		return copyOfRange(temp, j - zeroCount, temp.length);
	}

	/**
	 * decodeToBigInteger
	 * 
	 * @param input
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigInteger decodeToBigInteger(String input) throws IllegalArgumentException {
		return new BigInteger(1, decode(input));
	}

	/**
	 * divmod58
	 * 
	 * @param number
	 * @param startAt
	 * @return
	 */
	private static byte divmod58(byte[] number, int startAt) {
		int remainder = 0;
		for (int i = startAt; i < number.length; i++) {
			int digit256 = (int) number[i] & 0xFF;
			int temp = remainder * 256 + digit256;
			number[i] = (byte) (temp / 58);
			remainder = temp % 58;
		}
		return (byte) remainder;
	}

	/**
	 * divmod256
	 * 
	 * @param number58
	 * @param startAt
	 * @return
	 */
	private static byte divmod256(byte[] number58, int startAt) {
		int remainder = 0;
		for (int i = startAt; i < number58.length; i++) {
			int digit58 = (int) number58[i] & 0xFF;
			int temp = remainder * 58 + digit58;
			number58[i] = (byte) (temp / 256);
			remainder = temp % 256;
		}
		return (byte) remainder;
	}

	/**
	 * copyOfRange
	 * 
	 * @param source
	 * @param from
	 * @param to
	 * @return
	 */
	private static byte[] copyOfRange(byte[] source, int from, int to) {
		byte[] range = new byte[to - from];
		System.arraycopy(source, from, range, 0, range.length);
		return range;
	}
}