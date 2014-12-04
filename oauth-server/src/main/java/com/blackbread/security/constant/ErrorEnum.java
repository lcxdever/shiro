package com.blackbread.security.constant;

public enum ErrorEnum {
	INVALID_CLIENT("INVALID_CLIENT", "不可用的客户端ID"), UNAUTHORIZED_CLIENT(
			"UNAUTHORIZED_CLIENT", "未授权的客户端"), INVALID_GRANT(
			"INVALID_GRANT", "无效的许可CODE"), SERVER_ERROR("SERVER_ERROR",
			"server error,Please contact the administrator");
	private String code;
	private String destcrption;

	private ErrorEnum(String code, String destcrption) {
		this.code = code;
		this.destcrption = destcrption;
	}

	public String getCode() {
		return this.code;
	}

	public String getDestcrption() {
		return this.destcrption;
	}
}
