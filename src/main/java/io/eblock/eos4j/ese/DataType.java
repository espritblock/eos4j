package io.eblock.eos4j.ese;

/**
 * DataType
 * 
 * @author espritblock http://eblock.io
 *
 */
public enum DataType {

	name("name"), asset("asset"), string("string");

	private DataType(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
