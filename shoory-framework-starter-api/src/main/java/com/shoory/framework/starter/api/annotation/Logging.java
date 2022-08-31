package com.shoory.framework.starter.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.logging.LogLevel;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logging {
	LogLevel level() default LogLevel.TRACE;
	String action() default "";
	String description() default "";
}
