package com.alibaba.csp.sentinel.dashboard;

import com.alibaba.csp.sentinel.init.InitExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sentinel dashboard application.
 *
 * @author Carpenter Lee
 */
@SpringBootApplication
public class SocialSentinelApplication {

	public static void main(String[] args) {
		triggerSentinelInit();
		SpringApplication.run(SocialSentinelApplication.class, args);
	}

	private static void triggerSentinelInit() {
		new Thread(() -> InitExecutor.doInit()).start();
	}

}
