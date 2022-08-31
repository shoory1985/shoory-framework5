package com.shoory.framework.starter.jwt;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shoory.framework.starter.api.BizException;
import com.shoory.framework.starter.api.annotation.RequirePermission;
import com.shoory.framework.starter.api.request.AuthorizedRequest;
import com.shoory.framework.starter.api.response.AuthorizedResponse;

@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 2)
@Component
public class JwtAspect {
	private Logger logger = LoggerFactory.getLogger(JwtAspect.class);
	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * 环绕通知
	 * 
	 * @param joinPoint
	 * @return 返回被切入的方法的返回值
	 * @throws Throwable
	 */
	@Around("@annotation(requirePermission)")
	public Object doAround(ProceedingJoinPoint joinPoint, RequirePermission requirePermission) throws Throwable {
		//拣出JWT
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String token = attributes.getRequest().getHeader("Authorization");
		if (StringUtils.isBlank(token)) {
			throw new BizException(AuthorizedResponse.ERROR_ACCESS_TOKEN_MISSED);
		}
		//检查令牌有效性（合法性和是否过期）
		jwtUtils.checkAccessToken(token);

		//注入AuthorizedRequest
		Arrays.stream(joinPoint.getArgs())
			.findFirst()
			.filter(parameter -> parameter instanceof AuthorizedRequest)
			.ifPresent(parameter -> {
				DecodedJWT jwt = JWT.decode(token);
				if (StringUtils.isBlank(jwt.getSubject())) {
					throw new BizException(AuthorizedResponse.ERROR_INVALID_CREDENTIAL);
				}

				AuthorizedRequest ar = (AuthorizedRequest) parameter;
				ar.set_credential(jwt.getSubject());
				ar.set_scene(jwt.getAudience().stream().collect(Collectors.joining(",")));
			});

		Object retVal = joinPoint.proceed();// 这个方法会执行被切入的运行时方法，然后获取返回值，所以不会运行两次

		return retVal;
	}
}
