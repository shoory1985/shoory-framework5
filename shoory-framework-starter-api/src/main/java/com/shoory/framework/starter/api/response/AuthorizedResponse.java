package com.shoory.framework.starter.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AuthorizedResponse extends BaseResponse {
	public static final String ERROR_ACCESS_TOKEN_MISSED = "ERROR_ACCESS_TOKEN_MISSED";
	public static final String ERROR_ACCESS_TOKEN_EXPIRED = "ERROR_ACCESS_TOKEN_EXPIRED";
	public static final String ERROR_INVALID_ACCESS_TOKEN = "ERROR_INVALID_ACCESS_TOKEN";
	public static final String ERROR_INVALID_REFRESH_TOKEN = "ERROR_INVALID_REFRESH_TOKEN";
	public static final String ERROR_INVALID_CREDENTIAL = "ERROR_INVALID_CREDENTIAL";
}
