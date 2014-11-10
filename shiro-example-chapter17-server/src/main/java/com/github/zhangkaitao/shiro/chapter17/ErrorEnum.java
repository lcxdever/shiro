package com.github.zhangkaitao.shiro.chapter17;

public enum ErrorEnum {
	INVALID_CLIENT("INVALID_CLIENT", "不可用的客户端ID"), UNAUTHORIZED_CLIENT(
			"UNAUTHORIZED_CLIENT", "未授权的客户端"), UNAUTHORIZATION_CODE(
			"UNAUTHORIZATION_CODE", "未授权的认证CODE");
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
