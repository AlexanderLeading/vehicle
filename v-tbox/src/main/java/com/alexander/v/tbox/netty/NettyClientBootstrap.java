package com.alexander.v.tbox.netty;

import io.netty.bootstrap.Bootstrap;

import org.apache.log4j.Logger;

import com.accp.v.tbox.model.Vehicle;
import com.accp.v.tbox.utils.PropertiesUtils;

public class NettyClientBootstrap {

	protected static NettySocketClient nettySocketClient;

	private NettyClientBootstrap() {
		throw new IllegalAccessError("工具类不可实例化!");
	}

	static final Logger logger = Logger.getLogger(NettyClientBootstrap.class);

	// 存放连接服务器 配置信息
	static ClientConfig clientConfig;

	static {
		String configAll = PropertiesUtils.getProperty("netty.client.url");
		if (configAll == null || "".equals(configAll))
			throw new NullPointerException(
					"请检查ClassPath下config.properties文件中netty.client.url属性配置!");
		String[] configAllArray = configAll.trim().split(";", -1);
		for (String config : configAllArray) {
			if (config == null || "".equals(config))
				continue;
			// 解析各配置项
			String[] plate = config.split(":");
			clientConfig = new ClientConfig(plate[0],
					Integer.parseInt(plate[1]), plate[2], plate[3]);
		}
	}

	public static void init(Vehicle vehicle) {
		nettySocketClient = new NettySocketClient(clientConfig, vehicle);
		
		//
		nettySocketClient.connect(nettySocketClient
				.configureBootstrap(new Bootstrap()));
	}

	/**
	 * 根据 客户端名称获取 客户端
	 * 
	 * @param name
	 * @return
	 */
	public static NettySocketClient getClient() {
		return nettySocketClient;
	}
}
