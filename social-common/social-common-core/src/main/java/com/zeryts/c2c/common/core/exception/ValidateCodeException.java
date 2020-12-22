package com.zeryts.c2c.common.core.exception;

/**
 * 自定义异常: 检查失败
 * @author zeryts
 * @date 2018年06月22日16:22:15
 */
public class ValidateCodeException extends RuntimeException {

	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException() {
	}

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
