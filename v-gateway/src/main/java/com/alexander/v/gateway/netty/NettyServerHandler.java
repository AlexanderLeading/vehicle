package com.alexander.v.gateway.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;

import com.accp.v.gateway.model.Data;
import com.accp.v.gateway.upstream.UpStreamDataThread;
import com.accp.v.gateway.utils.JsonUtils;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = Logger
			.getLogger(NettyServerHandler.class);

	private int loss_connect_time = 0;

	/**
	 * 监听心跳的方法
	 * 
	 * @date 2017年7月6日,上午12:26:48
	 * @author chenyun
	 * @version 1.0.0
	 *
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext,
	 *      Object)
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;

			// IdleState.READER_IDLE 状态表示读者闲置,意味就是没收到数据
			if (event.state() == IdleState.READER_IDLE) {
				loss_connect_time++;
				System.out.println("5 秒没有接收到客户端的信息了");
				if (loss_connect_time > 2) {
					System.out.println("关闭这个不活跃的channel");
					ctx.channel().close();
				}
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	/**
	 * 监听通道获取数据的方法
	 * 
	 * @date 2017年7月6日,上午12:30:24
	 * @author chenyun
	 * @version 1.0.0
	 *
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext,
	 *      Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("收到数据");
		System.out.println(ctx.channel().remoteAddress() + "->Server :"
				+ msg.toString());
		Data data = (Data) JsonUtils.JSONToObject(String.valueOf(msg),
				Data.class);
		data.setChannel(ctx.channel());
		UpStreamDataThread.putUpStreamDataFromServerHandler(data);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * 通道被激活的方法
	 * 
	 * @date 2017年7月6日,上午12:30:47
	 * @author chenyun
	 * @version 1.0.0
	 *
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		try {
			super.channelActive(ctx);
			NettyChannelManager.getInstance().putChannel(ctx.channel());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
