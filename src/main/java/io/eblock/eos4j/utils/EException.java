package io.eblock.eos4j.utils;

/**
 * exception
 * 
 * @author espritblock  http://eblock.io
 *
 */
public class EException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String code;

	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}