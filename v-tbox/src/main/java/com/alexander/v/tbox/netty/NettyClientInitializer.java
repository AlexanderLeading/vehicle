package com.alexander.v.tbox.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import com.accp.v.tbox.utils.PropertiesUtils;


/**
 * Creates a newly configured {@link ChannelPipeline} for a server-side channel.
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
	

	private final static int readerIdleTimeSeconds = Integer
			.parseInt(PropertiesUtils.getProperty("netty.client.readerIdleTimeSeconds"));// 读操作空闲30秒

	private final static int writerIdleTimeSeconds = Integer
			.parseInt(PropertiesUtils.getProperty("netty.client.writerIdleTimeSeconds"));// 写操作空闲30秒

	private final static int allIdleTimeSeconds = Integer
			.parseInt(PropertiesUtils.getProperty("netty.client.allIdleTimeSeconds"));// 读写全部空闲60秒

	private NettySocketClient nettySocketClient = null;

	public NettyClientInitializer(NettySocketClient nettySocketClient) {
		this.nettySocketClient = nettySocketClient;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast(new NettyClientHandler(nettySocketClient));
	}
}
