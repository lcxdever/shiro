package com.blackbread.security.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blackbread.security.constant.ErrorEnum;
import com.blackbread.security.service.ClientService;
import com.blackbread.security.service.OAuthService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-16
 * <p>
 * Version: 1.0
 */
@Controller
public class AuthorizeController {

	@Autowired
	private OAuthService oAuthService;
	@Autowired
	private ClientService clientService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/authorize")
	public Object authorize(Model model, HttpServletRequest request)
			throws URISyntaxException, OAuthSystemException {

		try {
			// 构建OAuth 授权请求
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			// 验证oauthRequest，是否合法
			oAuthService.validate(oauthRequest);

			Subject subject = SecurityUtils.getSubject();
			// 如果用户没有登录，跳转到登陆页面
			if (!subject.isAuthenticated() && !subject.isRemembered()) {
				if (!login(subject, request)) {// 登录失败时跳转到登陆页面
					model.addAttribute("client", clientService
							.findByClientId(oauthRequest.getClientId()));
					return "oauth2login";
				}
			}
			String username = (String) subject.getPrincipal();

			String agreement = request.getParameter("agreement");
			if (agreement == null) {
				if (!clientService.findUserClient(username,
						oauthRequest.getClientId())) {
					return "userAuthorize";
				}
			} else {
				boolean agreement2 = Boolean.valueOf(request
						.getParameter("agreement"));
				if (agreement2) {
					clientService.inserUserClient(username,
							oauthRequest.getClientId());
				} else {
					
					return "error";
				}
			}
			// 生成授权码
			String authorizationCode = null;
			// responseType目前仅支持CODE，另外还有TOKEN
			String responseType = oauthRequest
					.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if (responseType.equals(ResponseType.CODE.toString())) {
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(
						new MD5Generator());
				authorizationCode = oauthIssuerImpl.authorizationCode();
				oAuthService.addAuthCode(authorizationCode, username);
			}
			// 进行OAuth响应构建
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
					.authorizationResponse(request,
							HttpServletResponse.SC_FOUND);
			// 设置授权码
			builder.setCode(authorizationCode);
			// 得到到客户端重定向地址
			String redirectURI = oauthRequest
					.getParam(OAuth.OAUTH_REDIRECT_URI);
			// 构建响应
			final OAuthResponse response = builder.location(redirectURI)
					.buildQueryMessage();

			// 根据OAuthResponse返回ResponseEntity响应
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response
					.getResponseStatus()));
		} catch (OAuthProblemException e) {
			return ExceptionHandler(e.getError(), e.getDescription());
		} catch (Exception e) {
			return ExceptionHandler(OAuthError.CodeResponse.SERVER_ERROR,
					ErrorEnum.SERVER_ERROR.getDestcrption());
		}
	}

	private Object ExceptionHandler(String error, String description) {
		// final OAuthResponse response = OAuthASResponse
		// .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		// .setError(e.getError())
		// .setErrorDescription(e.getDescription())
		// .buildJSONMessage();
		// return new ResponseEntity(response.getBody(),
		// HttpStatus.valueOf(response
		// .getResponseStatus()));
		Map<String, String> map = new HashMap<String, String>();
		map.put("error", error);
		map.put("error_description", description);
		ModelAndView mv = new ModelAndView("error", map);
		return mv;
	}

	private boolean login(Subject subject, HttpServletRequest request) {
		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean rememberMe = Boolean
				.valueOf(request.getParameter("rememberMe"));

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password, rememberMe);
		try {
			subject.login(token);
			return true;
		} catch (Exception e) {
			request.setAttribute("error", "登录失败:" + e.getClass().getName());
			return false;
		}
	}
}