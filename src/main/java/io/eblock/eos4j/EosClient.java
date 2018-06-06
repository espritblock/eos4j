package io.eblock.eos4j;

import io.eblock.eos4j.ecc.Ecc;
import io.eblock.eos4j.ese.Ese;

/**
 * EosClient
 * 
 * @author espritblock  http://eblock.io
 *
 */
public class EosClient {

	/**
	 * seedPrivate
	 * 
	 * @param seed
	 * @return
	 */
	public static String seedPrivate(String seed) {
		return Ecc.seedPrivate(seed);
	}

	/**
	 * privateToPublic
	 * 
	 * @param privateKey
	 * @return
	 */
	public static String privateToPublic(String privateKey) {
		return Ecc.privateToPublic(privateKey);
	}

	/**
	 * sign
	 * 
	 * @param privateKey
	 * @param data
	 * @return
	 */
	public static String sign(String privateKey, String data) {
		return Ecc.sign(privateKey, data);
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param quantity
	 * @param memo
	 * @return
	 */
	public static String parseTransferData(String from,String to,String quantity,String memo) {
		return Ese.parseTransferData(from,to,quantity,memo);
	}
	
	/**
	 * 
	 * @param creator
	 * @param name
	 * @param onwe
	 * @param active
	 * @return
	 */
	public static String parseAccountData(String creator,String name,String onwer,String active) {
		return Ese.parseAccountData(creator,name,onwer,active);
	}
}
