package com.tcdt.qlnvkhoach.config;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
	@Override
	public boolean supports(MethodParameter methodParameter, Type type,
			Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		try {
//			BaseRequestDto baseReq = (BaseRequestDto) body;
//			String uName = "";
//			uName = baseReq.getUserName();
//			MDC.put("username", uName);
//			ThreadContext.put("username", uName);
			log.info("Request RAW payload: " + new Gson().toJson(body));
		} catch (Exception e) {
		}
		return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
	}
}
