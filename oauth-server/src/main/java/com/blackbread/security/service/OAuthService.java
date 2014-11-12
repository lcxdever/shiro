package com.blackbread.security.service;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-17
 * <p>
 * Version: 1.0
 */
public interface OAuthService {

	// 添加 auth code
	public void addAuthCode(String authCode, String username);

	// 添加 access token
	public void addAccessToken(String accessToken, String username);

	// 验证auth code是否有效
	boolean checkAuthCode(String authCode);

	// 验证access token是否有效
	boolean checkAccessToken(String accessToken);

	String getUsernameByAuthCode(String authCode);

	String getUsernameByAccessToken(String accessToken);

	// auth code / access token 过期时间
	long getExpireIn();

	public boolean checkClientSecret(String clientSecret);

	public void validate(OAuthAuthzRequest oauthRequest)
			throws OAuthProblemException;

}
