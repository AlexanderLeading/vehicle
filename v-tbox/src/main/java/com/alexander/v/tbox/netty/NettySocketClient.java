package com.alexander.v.tbox.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.accp.v.tbox.downstream.RespondDataThread;
import com.accp.v.tbox.model.CommandTag;
import com.accp.v.tbox.model.Data;
import com.accp.v.tbox.model.Login;
import com.accp.v.tbox.model.Vehicle;
import com.accp.v.tbox.upstream.ReportDataThread;
import com.accp.v.tbox.utils.JsonUtils;
import com.accp.v.tbox.utils.PropertiesUtils;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;

public class NettySocketClient {

	static final Logger logger = Logger.getLogger(NettySocketClient.class);

	// 客户端连接通道
	private Channel channel;

	// 客户端接收消息 处理器 线程
	private RespondDataThread respondDataThread;

	// 客户端消息上报 处理 线程
	private ReportDataThread reportDataThread;

	// 客户端是否登录
	private boolean isLogined = false;


	private int loopThreads = 0;

	/**
	 * 当前重连次数
	 */
	private int currConnTimes = 0;

	private String ip;

	private int port;

	private Vehicle vehicle;

	/**
	 * 客户端唯一ID,事件驱动唯一标识
	 */
	private long clientId;

	public long getClientId() {
		return clientId;
	}

	public NettySocketClient(ClientConfig clientConfig, Vehicle vehicle) {
		this.ip = clientConfig.getIp();
		this.port = clientConfig.getPort();
		this.vehicle = vehicle;
		this.reportDataThread = new ReportDataThread("UpStreamThread", this); // 创建上行主线程
		this.respondDataThread = new RespondDataThread("DownStreamThread", this); // 创建回复主线程

		this.reportDataThread.init(); // 启动数据上报线程

		this.respondDataThread.init(); // 启动下行数据线程
	}

	public void connect(Bootstrap b) {
		ChannelFuture channelFuture = b.connect();
		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isDone() && future.isSuccess()) {
					channel = future.channel();
					logger.info("[Client(" + getClientId() + ")[客户端通道建立成功]");
					doLogin();
					// 重置重连次数
					currConnTimes = 0;
				}
				if (future.cause() != null) {
					// 失败后次数+1
					currConnTimes++;
					logger.error("[Client(" + getClientId() + ")][客户端通道建立失败]"
							+ future.cause());
					final EventLoop eventLoop = future.channel().eventLoop();
					eventLoop.schedule(new Runnable() {
						@Override
						public void run() {
							connect(configureBootstrap(new Bootstrap(),
									eventLoop));
						}

					}, getReConnInterval(), TimeUnit.SECONDS);
				}

			}
		});
	}

	/**
	 * 
	 * 配置Bootstrap
	 * 
	 * @param bootstrap
	 * 
	 * @return bootstrap
	 */
	public Bootstrap configureBootstrap(Bootstrap b) {
		return configureBootstrap(b, new NioEventLoopGroup(this.loopThreads,
				new NamedThreadFactory("NettyGroupThreadPool")));
	}

	public Bootstrap configureBootstrap(Bootstrap bootstrap,
			EventLoopGroup eventGroup) {
		bootstrap
				.group(eventGroup)
				.channel(NioSocketChannel.class)
				.remoteAddress(ip, port)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.RCVBUF_ALLOCATOR,
						AdaptiveRecvByteBufAllocator.DEFAULT)
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.handler(new NettyClientInitializer(this));
		return bootstrap;
	}

	/**
	 * 获取重连间隔 时间(秒)
	 */
	public int getReConnInterval() {
		int retryConntecMinutes = Integer.parseInt(PropertiesUtils
				.getRefreshProperty("netty.client.reconnectDelay"));
		if (currConnTimes <= 3) {
			return 60 * retryConntecMinutes;
		} else {
			return 60 * 30;
		}
	}

	/**
	 * 登陆后 发送业务数据
	 * 
	 */
	public boolean sendLoginedData(String data) {
		// 先判断是否已经登录
		if (isLogined()) {

			// 发送数据
			return sendData(data);
		} else {
			return false;
		}
	}

	/**
	 * 客户端是否已经可以发送数据
	 * 
	 * @return
	 */
	public boolean isLogined() {

		return isOk() && isLogined;
	}

	/**
	 * 客户端登录
	 */
	public void doLogin() {
		Login vehicleLogin = new Login();
		vehicleLogin.setCollectTime(System.currentTimeMillis() / 1000);
		vehicleLogin.setIccid(vehicle.getIccid());
		vehicleLogin.setSerialNumber(1);
		String loginJson = JsonUtils.toJSONString(vehicleLogin);
		Data data = new Data();
		data.setCommandTag(CommandTag.VEHICLE_LOGIN);
		data.setVin(vehicle.getVin());
		data.setContent(loginJson);
		String json = JsonUtils.toJSONString(data);
		sendData(json);
	}

	/**
	 * 发送数据 二进制字节序列
	 * 
	 */
	public boolean sendData(String json) {
		// 看通道的状态是否是正确
		if (isOk()) {
			
			// 发送数据
			channel.writeAndFlush(json);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 客户端是否已经可以发送数据
	 * 
	 * @return
	 */
	public boolean isOk() {
		boolean isOk = channel != null && channel.isOpen()
				&& channel.isActive();
		return isOk;
	}


	public RespondDataThread getRespondDataThread() {
		return respondDataThread;
	}

	public void setRespondDataThread(RespondDataThread respondDataThread) {
		this.respondDataThread = respondDataThread;
	}

	public ReportDataThread getReportDataThread() {
		return reportDataThread;
	}

	public void setReportDataThread(ReportDataThread reportDataThread) {
		this.reportDataThread = reportDataThread;
	}

	public void setLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
