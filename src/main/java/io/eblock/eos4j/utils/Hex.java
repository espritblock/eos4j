package io.eblock.eos4j.utils;

/**
 * Hex
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Hex {

	/**
	 * toBytes
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] toBytes(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			throw new EException("args_eroor", "args is error");
		}
		char[] hbyte = hex.toCharArray();
		int length = hbyte.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hbyte[i * 2], 16);
			int low = Character.digit(hbyte[i * 2 + 1], 16);
			if (high < 0 || low < 0) {
				throw new RuntimeException("Invalid hex digit " + hbyte[i * 2] + hbyte[i * 2 + 1]);
			}
			int value = (high << 4) | low;
			if (value > 127)
				value -= 256;
			raw[i] = (byte) value;
		}
		return raw;
	}
}
