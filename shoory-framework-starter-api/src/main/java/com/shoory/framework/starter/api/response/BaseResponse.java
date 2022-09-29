package com.shoory.framework.starter.api.response;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR_UNKNOWN = "ERROR_UNKNOWN";
	public static final String ERROR_INTERNAL = "ERROR_INTERNAL";
	public static final String ERROR_INVALID_PARAMETERS = "ERROR_INVALID_PARAMETERS";
	public static final String ERROR_OPERATION_FORBIDDEN = "ERROR_OPERATION_FORBIDDEN";
	
	@Schema(title = "响应代码", example = "SUCCESS")
	private String code = "SUCCESS";

	@Schema(title = "响应消息", example = "")
	private String message;
	
	@JsonIgnore
	public boolean isSuccess() {
		return SUCCESS.equals(this.code);
	}
}
