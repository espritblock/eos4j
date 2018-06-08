package io.eblock.eos4j.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class ErrorDetails {

	private String message;

	private String file;

	private Integer lineNumber;

	private String method;

	private ErrorDetails() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	@JsonProperty("line_number")
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
