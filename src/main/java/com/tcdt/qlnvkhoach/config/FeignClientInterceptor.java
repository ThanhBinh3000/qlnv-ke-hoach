package com.tcdt.qlnvkhoach.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";

	public static String getBearerTokenHeader() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader(AUTHORIZATION_HEADER);
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader());

	}
}
