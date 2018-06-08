package io.eblock.eos4j.api.vo.transaction.push;

import io.eblock.eos4j.api.vo.BaseVo;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class TxRequest extends BaseVo{

	public TxRequest() {

	}

	public TxRequest(String compression, Tx transaction, String[] signatures) {
		this.compression = compression;
		this.transaction = transaction;
		this.signatures = signatures;
	}

	private String compression;

	private Tx transaction;

	private String[] signatures;

	public String getCompression() {
		return compression;
	}

	public void setCompression(String compression) {
		this.compression = compression;
	}

	public Tx getTransaction() {
		return transaction;
	}

	public void setTransaction(Tx transaction) {
		this.transaction = transaction;
	}

	public String[] getSignatures() {
		return signatures;
	}

	public void setSignatures(String[] signatures) {
		this.signatures = signatures;
	}

}
