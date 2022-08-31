package com.shoory.framework.starter.api;

public class BizException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

	public BizException(String code) {
		super(code, null, false, false);
		this.code = code;
	}
	public BizException(String code, String message) {
		super(code, null, false, false);
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}
	public String getMessage() {
		return this.message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
