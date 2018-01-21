package com.alexander.v.tbox.upstream;

import org.apache.log4j.Logger;

import com.accp.v.tbox.model.Data;
import com.accp.v.tbox.netty.NettySocketClient;
import com.accp.v.tbox.utils.JsonUtils;

public class ReportDataTask implements Runnable {

	private static Logger logger = Logger.getLogger(ReportDataTask.class);

	private NettySocketClient nettySocketClient;

	// private EhcacheManagerService ehcacheService =
	// SpringContextUtils.getBean("ehcacheManagerService");

	private Data data;

	public ReportDataTask(NettySocketClient nettySocketClient, Data data) {
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
			if (data.getCommandTag() == null)
				return;
			String json = JsonUtils.toJSONString(data);

			// 发送数据
			nettySocketClient.sendLoginedData(json);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 从 上报实时数据和补发数据 中获取vin+collecttim组合主键key
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getKeyFromReportBytes(byte[] bytes) {
		String vin = "";
		String time = "";
		return vin + time;
	}

	/**
	 * 从 上报实时数据和补发数据 中获取vin+collecttim组合主键key
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getKeyFromRespondBytes(byte[] bytes) {
		String vin = "";
		String time = "";
		return vin + time;
	}
}
