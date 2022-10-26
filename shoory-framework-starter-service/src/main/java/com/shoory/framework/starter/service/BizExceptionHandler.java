package com.shoory.framework.starter.service;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shoory.framework.starter.api.BizException;
import com.shoory.framework.starter.api.response.BaseResponse;

@ControllerAdvice
public class BizExceptionHandler {
	@Autowired
	private I18nComponent i18nComponent;

	// 捕捉到的业务异常
	@ExceptionHandler(value = BizException.class)
	public ResponseEntity<BaseResponse> handleServiceException(BizException bizException) {
		BaseResponse response = new BaseResponse();
		response.setCode(bizException.getCode());
		response.setCode(bizException.getMessage());

		if (response.getMessage().equals(response.getCode())) {
			response.setMessage(Optional.ofNullable(i18nComponent.getMessage(response.getCode(), "zh_CN"))
					.orElse(response.getCode()));
		}

		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}

	// 捕捉到的入参校验异常
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponse> handleServiceException(MethodArgumentNotValidException exception) {
		BaseResponse response = new BaseResponse();
		response.setCode(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		response.setMessage(Optional.ofNullable(i18nComponent.getMessage(response.getCode(), "zh_CN"))
				.orElse(response.getCode()));
//		response.setMessage(exception.getBindingResult().getAllErrors().stream()
//				.map(ObjectError::getDefaultMessage)
//				.collect(Collectors.joining()));

		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}

	// 其他异常
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<BaseResponse> hadleServerException(Exception exception) {
		exception.printStackTrace();
		BaseResponse response = new BaseResponse();
		response.setCode(BaseResponse.ERROR_INTERNAL);
		response.setMessage(exception.getMessage());
		
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
}