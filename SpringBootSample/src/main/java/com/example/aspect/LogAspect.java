package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	/**
	 * サービスの実行前にログ出力
	 * 対象は[Hello]をクラス名に含んでいる
	 */
	@Before("execution(* com.example.hello.*Hello*.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("メソッド開始:" + jp.getSignature());
	}

	/**
	 * サービスの実行後にログ出力
	 * 対象は[Hello]をクラス名に含んでいる
	 */
	@After("execution(* com.example.hello.*Hello*.*(..))")
	public void endtLog(JoinPoint jp) {
		log.info("メソッド終了:" + jp.getSignature());
	}

	/** 全部のコントローラーの実行前後にログ出力*/
	@Around("@within(org.springframework.stereotype.Controller)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {

		log.info("メソッド開始" + jp.getSignature());

		try {
			Object result = jp.proceed();
			log.info("メソッド終了:" + jp.getSignature());

			return result;

		} catch (Exception e) {
			log.error("メソッド異常終了:" + jp.getSignature());
			//エラーを再度投げる
			throw e;
		}
	}

}
