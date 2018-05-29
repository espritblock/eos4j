package io.eblock.eos4j.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.stream.IntStream;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class ByteUtils {

	static String charmap = ".12345abcdefghijklmnopqrstuvwxyz";

	/**
	 * charidx
	 * 
	 * @param c
	 * @return
	 */
	public static int charidx(char c) {
		return charmap.indexOf(c);
	}

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

	/**
	 * LongToBytes
	 * 
	 * @param values
	 * @return
	 */
	public static int[] LongToBytes(Long n) {
		ByteBuffer hi = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.BIG_ENDIAN).putLong(n);
		byte[] buf = hi.array();
		int[] a = IntStream.range(0, buf.length).map(i -> buf[i] & 0xff).toArray();
		return a;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String stringToAscii(String value)  
	{  
	    StringBuffer sbu = new StringBuffer();  
	    char[] chars = value.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	        if(i != chars.length - 1)  
	        {  
	            sbu.append((int)chars[i]);  
	        }  
	        else {  
	            sbu.append((int)chars[i]);  
	        }  
	    }  
	    return sbu.toString();  
	}  

}
