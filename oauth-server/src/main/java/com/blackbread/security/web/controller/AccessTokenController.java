package com.blackbread.security.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.blackbread.security.constant.Constants;
import com.blackbread.security.constant.ErrorEnum;
import com.blackbread.security.service.OAuthService;
import com.blackbread.security.service.UserService;

/**
 * 
 * @Description:获取accessstoken过程
 * @author :blackbread
 * @time :2014年11月25日 下午10:02:23
 * @version :
 */
@RestController
public class AccessTokenController {

	@Autowired
	private OAuthService oAuthService;

	@Autowired
	private UserService userService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/accessToken")
	public Object token(HttpServletRequest request) throws OAuthSystemException {

		try {
			// 构建OAuth请求
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
			// 检查客户端安全KEY是否正确
			oAuthService.checkClientSecret(oauthRequest.getClientId(),
					oauthRequest.getClientSecret());
			String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
			// 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.AUTHORIZATION_CODE.toString())) {
				oAuthService.checkAuthCode(authCode);
			}
			// 生成Access Token
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(
					new MD5Generator());
			final String accessToken = oauthIssuerImpl.accessToken();
			oAuthService.addAccessToken(accessToken,
					oAuthService.getUsernameByAuthCode(authCode));
			// 生成OAuth响应
			OAuthResponse response = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken)
					.setExpiresIn(String.valueOf(oAuthService.getExpireIn()))
					.buildJSONMessage();
			// 根据OAuthResponse生成ResponseEntity
			return new ResponseEntity(response.getBody(),
					HttpStatus.valueOf(response.getResponseStatus()));
		} catch (OAuthProblemException e) {
			return ExceptionHandler(e.getError(), e.getDescription());
		}
	}
	private Object ExceptionHandler(String error, String description) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("error", error);
		map.put("error_description", description);
		return map;
	}
}
