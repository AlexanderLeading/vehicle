package com.alexander.v.tbox.downstream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.alexander.v.tbox.model.CommandTag;
import com.alexander.v.tbox.model.Data;
import com.alexander.v.tbox.netty.NettySocketClient;
import org.apache.log4j.Logger;


public class RespondDataTask implements Runnable {

	private static Logger logger = Logger.getLogger(RespondDataThread.class);

	private Data data;

	/**
	 * Netty客户端连接对象
	 */
	private NettySocketClient nettySocketClient;

	static final DateFormat dFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	RespondDataTask(NettySocketClient nettySocketClient, Data data) {
		this.nettySocketClient = nettySocketClient;
		this.data = data;
	}

	/**
	 *
	 * @see Runnable#run()
	 */
	@Override
	public void run() {
		try {
			if (data.getCommandTag() == CommandTag.VEHICLE_LOGIN) {
				// 如果收到的报文是登陆回复,那么就需要把登陆标示改成true
				nettySocketClient.setLogined(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + "---data=");
		}
	}

}
