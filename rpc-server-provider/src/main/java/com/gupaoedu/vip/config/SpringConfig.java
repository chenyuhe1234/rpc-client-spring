package com.gupaoedu.vip.config;


import com.gupaoedu.vip.GpRpcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.gupaoedu.vip")
public class SpringConfig {


	/**
	 * 向容器中注入gpRpcServer
	 */
	@Bean(name = "gpRpcServer")
	public GpRpcServer gpRpcServer() {
		return new GpRpcServer(8080);
	}
}