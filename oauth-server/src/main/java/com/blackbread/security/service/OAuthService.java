package com.blackbread.security.service;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import com.blackbread.security.entity.Client;

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
	void checkAuthCode(String authCode) throws OAuthProblemException;

	// 验证access token是否有效
	boolean checkAccessToken(String accessToken);

	String getUsernameByAuthCode(String authCode);

	String getUsernameByAccessToken(String accessToken);

	// auth code / access token 过期时间
	long getExpireIn();

	public void checkClientSecret(String clientID, String clientSecret)
			throws OAuthProblemException;

	/**
	 * 用于验证客户端url调用参数信息，并返回请求的客户端信息
	 * 
	 * @param oauthRequest
	 * @return
	 * @throws OAuthProblemException
	 */
	public Client validate(OAuthAuthzRequest oauthRequest)
			throws OAuthProblemException;

}
