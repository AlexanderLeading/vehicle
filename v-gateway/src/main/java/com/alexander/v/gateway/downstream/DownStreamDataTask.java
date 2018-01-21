package com.alexander.v.gateway.downstream;

import com.alexander.v.gateway.model.Data;
import com.alexander.v.gateway.netty.NettyChannelManager;
import com.alexander.v.gateway.utils.JsonUtils;
import io.netty.channel.Channel;

import org.apache.log4j.Logger;



public class DownStreamDataTask implements Runnable {

	private static Logger logger = Logger.getLogger(DownStreamDataThread.class);

	private Data data;

	DownStreamDataTask(Data data) {
		this.data = data;
	}

	/**
	 *
	 * @see Runnable#run()
	 */
	@Override
	public void run() {
		try {
			if (data == null) {
				return;
			}
			String vin = data.getVin();
			if (vin == null) {
				return;
			}
			// 通过VIN从通道的缓存中获取通过通道对象
			Channel channel = NettyChannelManager.getInstance()
					.getChannelByUid(vin);
			if (channel == null) {
				logger.error("[DownStreamDataTask]未找到[" + vin + "]["
						+ JsonUtils.toJSONString(data) + "]对应的Channel!");
				return;
			}
			// 给车辆发送回复命令
			channel.writeAndFlush(JsonUtils.toJSONString(data));
			logger.info("[DownStreamDataTask][下行报文][Vin=" + vin + "]("
					+ channel.id().asLongText() + ")("
					+ channel.remoteAddress().toString() + ")]["
					+ JsonUtils.toJSONString(data) + "]");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
