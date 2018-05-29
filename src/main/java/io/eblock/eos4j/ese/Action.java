package io.eblock.eos4j.ese;

/**
 * Action
 * 
 * @author espritblock http://eblock.io
 *
 */
public enum Action {

	transfer("4,${quantity}@eosio.token");

	private String code;

	private Action(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}