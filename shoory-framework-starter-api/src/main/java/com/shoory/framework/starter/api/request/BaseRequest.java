package com.shoory.framework.starter.api.request;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRequest implements Serializable {
	@Schema(title = "语言", description = "默认zh_CN", example = "zh_CN", defaultValue = "zh_CN")
	private String lang = "zh_CN";
	
	@Schema(title = "地址", hidden = true)
	private String _clientAddress;

	@JsonIgnore
	@Schema(hidden = true)
	private HashMap<String, String> _extendInfo;	
}
