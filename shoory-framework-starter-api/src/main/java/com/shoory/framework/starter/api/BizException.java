package com.shoory.framework.starter.api;

public class BizException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;

	public BizException(String code) {
		super(code, null, false, false);
		this.code = code;
	}
	public BizException(String code, String message) {
		super(message, null, false, false);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
