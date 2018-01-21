package com.alexander.v.tbox.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.accp.v.tbox.model.CommandTag;
import com.accp.v.tbox.model.Data;
import com.accp.v.tbox.utils.JsonUtils;

@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	static final Logger logger = Logger.getLogger(NettyClientHandler.class);

	NettySocketClient nettySocketClient;

	private String heartbeat;

	public NettyClientHandler(NettySocketClient nettySocketClient) {
		this.nettySocketClient = nettySocketClient;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// 下行的数据
		String json = String.valueOf(msg);
		Data data = (Data) JsonUtils.JSONToObject(json, Data.class);
		this.nettySocketClient.getRespondDataThread().put(data);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		final EventLoop eventLoop = ctx.channel().eventLoop();
		final NettySocketClient client = this.nettySocketClient;
		eventLoop.schedule(new Runnable() {
			@Override
			public void run() {
				client.connect(client.configureBootstrap(new Bootstrap(),
						eventLoop));
			}

		}, client.getReConnInterval(), TimeUnit.SECONDS);
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
		// logger.info("channelReadComplete--->");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		// TODO Auto-generated method stub
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			switch (e.state()) {
			case WRITER_IDLE:
				logger.info("心跳");

				// 心跳包
				Data data = new Data();
				data.setVin(nettySocketClient.getVehicle().getVin());
				data.setCommandTag(CommandTag.HEARTBEAT);
				heartbeat = JsonUtils.toJSONString(data);

				// 把心跳发到上报的线程中
				NettyClientBootstrap.getClient().getReportDataThread()
						.put(data);
				break;
			case READER_IDLE:

				break;
			case ALL_IDLE:

				break;
			default:
				break;
			}
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)
			throws Exception {
		super.channelWritabilityChanged(ctx);
	}
}
