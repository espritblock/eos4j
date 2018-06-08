package io.eblock.eos4j.api.exception;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApiError error;

	public ApiException(ApiError apiError) {
		this.error = apiError;
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		if (error != null) {
			return error.getMessage();
		}
		return super.getMessage();
	}
}
