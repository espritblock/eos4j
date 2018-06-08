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

	/**
	 * bytesToHexString
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * hexStringToBytes
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
