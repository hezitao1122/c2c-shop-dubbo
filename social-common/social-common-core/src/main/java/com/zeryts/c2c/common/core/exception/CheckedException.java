package com.zeryts.c2c.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * 自定义异常: 校验失败
 * @author zeryts
 * @date 😴2018年06月22日16:21:57
 */
@NoArgsConstructor
public class CheckedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CheckedException(String message) {
		super(message);
	}

	public CheckedException(Throwable cause) {
		super(cause);
	}

	public CheckedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
