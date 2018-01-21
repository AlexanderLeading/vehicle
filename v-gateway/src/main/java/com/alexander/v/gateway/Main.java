package com.alexander.v.gateway;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.accp.v.gateway.netty.NettyServerBootstrap;

public class Main {

	public static void main(String[] args) throws Exception {

		// 通过Spring记载配置文件
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:application.xml");// 此文件
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 6666;
		}
		// 创建Netty Server
		new NettyServerBootstrap(port).start();
	}
}
