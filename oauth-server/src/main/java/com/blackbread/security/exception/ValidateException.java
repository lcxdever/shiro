package com.blackbread.security.exception;

public class ValidateException extends Exception {
	private static final long serialVersionUID = 1L;
	private String code;
	private String info;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ValidateException(String code, String info) {
		super();
		this.code = code;
		this.info = info;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer(100);
		sb.append("error code:").append(this.code).append(",error info:")
				.append(this.info);
		return sb.toString();
	}
}
