package io.eblock.eos4j.utils;

/**
 * 
 * @author espritblock  http://eblock.io
 *
 */
public class ByteUtils {

	/**
	 * concat
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] concat(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * copy
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] copy(byte[] src, int start, int length) {
		byte[] c = new byte[length];
		System.arraycopy(src, start, c, 0, length);
		return c;
	}

	/**
	 * copy
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] copy(byte[] src, int start, byte[] dest, int dstart, int length) {
		System.arraycopy(src, start, dest, dstart, length);
		return dest;
	}

}
