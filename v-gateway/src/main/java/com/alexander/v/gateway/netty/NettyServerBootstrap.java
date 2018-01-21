package com.alexander.v.gateway.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.accp.v.gateway.downstream.DownStreamDataThread;
import com.accp.v.gateway.upstream.UpStreamDataThread;

public class NettyServerBootstrap {

	private static final Logger logger = Logger
			.getLogger(NettyServerBootstrap.class);

	private DownStreamDataThread downStreamDataThread;

	private UpStreamDataThread upStreamDataThread;

	private int port;

	public NettyServerBootstrap(int port) {
		this.downStreamDataThread = new DownStreamDataThread();
		this.upStreamDataThread = new UpStreamDataThread("UpStreamDataThread");
		this.port = port;
	}

	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sbs = new ServerBootstrap()
					.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.localAddress(new InetSocketAddress(port))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new IdleStateHandler(30, 0, 0,
											TimeUnit.SECONDS));
							ch.pipeline().addLast("decoder",
									new StringDecoder());
							ch.pipeline().addLast("encoder",
									new StringEncoder());
							ch.pipeline().addLast(new NettyServerHandler());
						};

					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			// 绑定端口，开始接收进来的连接
			ChannelFuture future = sbs.bind(port).sync();

			if (future.isSuccess()) {
				this.upStreamDataThread.init();
				this.downStreamDataThread.init();
				logger.info("[GatewayServer]服务启动成功，端口号：" + this.port);
			}

			System.out.println("Server start listen at " + port);
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
