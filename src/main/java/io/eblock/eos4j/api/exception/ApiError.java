package io.eblock.eos4j.api.exception;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class ApiError {

	private String message;

	private String code;

	private Error error;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
