package io.eblock.eos4j.ese;

import io.eblock.eos4j.utils.ByteUtils;
import io.eblock.eos4j.utils.Sha;

/**
 * Ese
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Ese {
	
	
	/**
	 * parseData
	 * @param datas
	 * @return
	 */
	public static String parseData(DataParam [] datas) {
		byte [] allbyte  = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte,value.seria());
		}
//		final byte [] b = allbyte.clone();
//		int[] a =  IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
//		for(int i=1;i<=a.length;i++) {
//			System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
//		}
		return Sha.bytesToHexString(allbyte);
	}
}
