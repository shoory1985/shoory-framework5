package com.shoory.framework.starter.db;

import com.shoory.framework.starter.db.annotation.NeedRetry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**

 重试切面

 @author gourd
 */
@Aspect
@Component
@Slf4j
public class NeedRetryAspect {
	@Pointcut("@annotation(com.shoory.framework.starter.db.annotation.NeedRetry)")
	public void retryOnOptFailure() {
	}

	@Around("retryOnOptFailure()")
	@Transactional(rollbackFor = Throwable.class)
	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
		// 获取拦截的方法名
		MethodSignature msig = (MethodSignature) pjp.getSignature();
		// 返回被织入增加处理目标对象
		Object target = pjp.getTarget();
		// 为了获取注解信息
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		// 获取注解信息
		NeedRetry annotation = currentMethod.getAnnotation(NeedRetry.class);
		// 重试次数
		int numAttempts = 0;
		do {
			numAttempts++;
			try {
				// 第numAttempts次执行业务代码
				return pjp.proceed();
			} catch (ObjectOptimisticLockingFailureException | StaleObjectStateException ex) {
				if (numAttempts > annotation.retryTimes()) {
					// 如果大于 默认的重试机制 次数，我们这回就真正的抛出去了
					throw ex;
				} else {
					// 如果 没达到最大的重试次数，将再次执行
					log.info("0 === 正在重试=====" + numAttempts + "次");
				}
			}
		} while (numAttempts <= annotation.retryTimes());

		return null;
	}
}