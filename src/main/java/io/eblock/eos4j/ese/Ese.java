package io.eblock.eos4j.ese;

import java.util.ArrayList;
import java.util.List;

import io.eblock.eos4j.utils.ByteUtils;
import io.eblock.eos4j.utils.Hex;

/**
 * Ese
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Ese {

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseTransferData(String from, String to, String quantity, String memo) {
		DataParam[] datas = new DataParam[] { new DataParam(from, DataType.name, Action.transfer),
				new DataParam(to, DataType.name, Action.transfer),
				new DataParam(quantity, DataType.asset, Action.transfer),
				new DataParam(memo, DataType.string, Action.transfer), };
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		// final byte [] b = allbyte.clone();
		// int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
		// for(int i=1;i<=a.length;i++) {
		// System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
		// }
		return Hex.bytesToHexString(allbyte);
	}
	

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseVoteProducerData(String voter, String proxy, List<String> producers) {
		List<DataParam> datas = new ArrayList<DataParam>();
		datas.add(new DataParam(voter, DataType.name, Action.voteproducer));
		datas.add(new DataParam(proxy, DataType.name, Action.voteproducer));
		datas.add(new DataParam(String.valueOf(producers.size()), DataType.varint32, Action.voteproducer));
		for(String producer:producers) {
			datas.add(new DataParam(producer, DataType.name, Action.voteproducer));
		}
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
//		 final byte [] b = allbyte.clone();
//		 int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
//		 for(int i=1;i<=a.length;i++) {
//		 System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
//		 }
		return Hex.bytesToHexString(allbyte);
	}
	
	

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseAccountData(String creator, String name, String onwer, String active) {

		DataParam[] datas = new DataParam[] {
				// creator
				new DataParam(creator, DataType.name, Action.account),
				// name
				new DataParam(name, DataType.name, Action.account),
				// owner
				new DataParam(onwer, DataType.key, Action.account),
				// active
				new DataParam(active, DataType.key, Action.account),

		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}

	/**
	 * parseBuyRamData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseDelegateData(String from, String receiver, String stakeNetQuantity,
			String stakeCpuQuantity, int transfer) {

		DataParam[] datas = new DataParam[] { new DataParam(from, DataType.name, Action.delegate),
				new DataParam(receiver, DataType.name, Action.delegate),
				new DataParam(stakeNetQuantity, DataType.asset, Action.delegate),
				new DataParam(stakeCpuQuantity, DataType.asset, Action.delegate),
				new DataParam(String.valueOf(transfer), DataType.varint32, Action.delegate)

		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseBuyRamData(String payer, String receiver, Long bytes) {

		DataParam[] datas = new DataParam[] { new DataParam(payer, DataType.name, Action.ram),
				new DataParam(receiver, DataType.name, Action.ram),
				new DataParam(String.valueOf(bytes), DataType.unit32, Action.ram)

		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}
	
	/**
	 * parseCloseData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseCloseData(String owner, String symbol) {
		DataParam[] datas = new DataParam[] { 
			new DataParam(owner, DataType.name, Action.close),
			new DataParam(symbol, DataType.symbol, Action.close)
		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}
}
