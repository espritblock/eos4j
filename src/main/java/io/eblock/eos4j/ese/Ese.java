package io.eblock.eos4j.ese;

import java.util.stream.IntStream;

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
	 * parseTransferData
	 * @param datas
	 * @return
	 */
	public static String parseTransferData(String from,String to,String quantity,String memo) {
		DataParam [] datas = new DataParam[] {
			new DataParam(from,DataType.name,Action.transfer),
			new DataParam(to,DataType.name,Action.transfer),
			new DataParam(quantity,DataType.asset,Action.transfer),
			new DataParam(memo,DataType.string,Action.transfer),
		};
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
	
	/**
	 * parseTransferData
	 * @param datas
	 * @return
	 */
	public static String parseAccountData(String creator,String name,String onwer,String active) {
		DataParam [] datas = new DataParam[] {
				//creator
				new DataParam(creator, DataType.name, Action.account),
				//name
				new DataParam(name, DataType.name, Action.account),
				
				//onwer-threshold
				new DataParam("1", DataType.unit32, Action.account),
				//onwer-account-length
				new DataParam("1", DataType.varint32, Action.account),
				//onwer-key-typePosition
				new DataParam("0", DataType.varint32, Action.account),
				//onwer-key
				new DataParam(onwer, DataType.key, Action.account),
				//onwer-weight
				new DataParam("1", DataType.unit16, Action.account),
				//onwer-account-length
				new DataParam("0", DataType.varint32, Action.account),
				//onwer-wait-length
				new DataParam("0", DataType.varint32, Action.account),
				
				//active-threshold
				new DataParam("1", DataType.unit32, Action.account),
				//active-account-length
				new DataParam("1", DataType.varint32, Action.account),
				//active-key-typePosition
				new DataParam("0", DataType.varint32, Action.account),
				//active-key
				new DataParam(active, DataType.key, Action.account),
				//active-weight
				new DataParam("1", DataType.unit16, Action.account),
				//active-account-length
				new DataParam("0", DataType.varint32, Action.account),
				//active-wait-length
				new DataParam("0", DataType.varint32, Action.account),
		};
		byte [] allbyte  = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte,value.seria());
		}
//		 final byte [] b = allbyte.clone();
//		 int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
//		 for(int i=1;i<=a.length;i++) {
//		 System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
//		 }
		return Sha.bytesToHexString(allbyte);
	}
}
