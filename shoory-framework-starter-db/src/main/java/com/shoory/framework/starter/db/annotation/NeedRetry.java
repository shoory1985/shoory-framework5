package com.shoory.framework.starter.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedRetry {

	/**
	 重试次数
	 @return
	 */
	int retryTimes() default 3;
}