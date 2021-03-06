package com.blackbread.security.service;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.blackbread.security.constant.ErrorEnum;
import com.blackbread.security.dao.UserDao;
import com.blackbread.security.entity.Client;
import com.blackbread.security.utils.StringUtil;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-17
 * <p>
 * Version: 1.0
 */
@Service
public class OAuthServiceImpl implements OAuthService {

	private Cache cache;

	@Autowired
	private ClientService clientService;
	@Autowired
	private UserDao userDao;

	@Autowired
	public OAuthServiceImpl(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("code-cache");
	}

	@Override
	public void addAuthCode(String authCode, String username) {
		cache.put(authCode, username);
	}

	@Override
	public void addAccessToken(String accessToken, String username) {
		cache.put(accessToken, username);
	}

	@Override
	public String getUsernameByAuthCode(String authCode) {
		return (String) cache.get(authCode).get();
	}

	@Override
	public String getUsernameByAccessToken(String accessToken) {
		return (String) cache.get(accessToken).get();
	}

	@Override
	public void checkAuthCode(String authCode) throws OAuthProblemException {
		if(cache.get(authCode) == null)
		throw OAuthProblemException.error(
				OAuthError.TokenResponse.INVALID_GRANT,ErrorEnum.INVALID_GRANT.getDestcrption());
	}

	@Override
	public boolean checkAccessToken(String accessToken) {
		return cache.get(accessToken) != null;
	}

	@Override
	public void checkClientSecret(String clientID,String clientSecret) throws OAuthProblemException {
		if( clientService.findByClientSecret(clientID,clientSecret) == null)
			throw OAuthProblemException.error(
					OAuthError.TokenResponse.UNAUTHORIZED_CLIENT, ErrorEnum.UNAUTHORIZED_CLIENT.getDestcrption());
	}

	@Override
	public long getExpireIn() {
		return 3600L;
	}

	@Override
	public Client validate(OAuthAuthzRequest oauthRequest)
			throws OAuthProblemException {
		String errorInfo;
		String redirectURI = oauthRequest.getRedirectURI();
		if (StringUtil.isEmpty(redirectURI)) {
			errorInfo = "redirect_uri is empty";
			throw OAuthProblemException.error(
					OAuthError.CodeResponse.INVALID_REQUEST, errorInfo);
		}
		if (!ResponseType.CODE.toString()
				.equals(oauthRequest.getResponseType())) {
			errorInfo = "unsupported response type,the response type must code";
			throw OAuthProblemException.error(
					OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
					errorInfo);
		}
		Client client = clientService
				.findByClientId(oauthRequest.getClientId());
		if (client == null) {
			errorInfo = "Can not find the client_id:"
					+ oauthRequest.getClientId();
			throw OAuthProblemException.error(
					OAuthError.CodeResponse.INVALID_REQUEST, errorInfo);
		}
		if (oauthRequest.getRedirectURI().equals("abc")) {
			errorInfo = "application callback can not match the redirect_uri";
			throw OAuthProblemException.error(
					OAuthError.TokenResponse.INVALID_CLIENT, errorInfo);
		}
		return client;
	}
}
