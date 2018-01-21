package com.alexander.v.tbox;

import com.accp.v.tbox.model.Vehicle;
import com.accp.v.tbox.netty.NettyClientBootstrap;

public class Main {
	public static void main(String[] args) throws Exception {

		Vehicle vehicle = new Vehicle(); // 创建车辆对象
		vehicle.setVin("MMV2ARB25G0010724"); // 设置车辆的VIN
		vehicle.setIccid("898602B2331530010724"); // 设置通信的IccId(流量卡的通信标示)

		NettyClientBootstrap.init(vehicle); // 初始化Netty 客户端
		/*
		// 循环的发送数据报文
		while (true) {
			Data data = new Data();
			data.setCommandTag(CommandTag.REALTIME_INFO_REPORT); // 设置标记为实时信息上报
			RealInfo realInfo = new RealInfo(); // 创建实时数据
			WholeInfo wholeInfo = new WholeInfo(); // 创建整车数据
			realInfo.setWholeInfo(wholeInfo); // 设置整车数据
			// realInfo.set
			String json = JsonUtils.toJSONString(realInfo);
			data.setContent(json);
			NettyClientBootstrap.getClient().getReportDataThread().put(data); // 把需要上报的报文新增到上报的队列中
			Thread.sleep(10000); // 线程休眠10秒(每隔10秒发送一次实时报文)
		}
		*/
		// System.out.println( Integer.MAX_VALUE);
		// System.out.print( Integer.MAX_VALUE-1498578925);
		
		//UUID id=UUID.randomUUID();
		//System.out.println(id.toString());
		//System.out.println(id.toString().length());
		
		//数据库的主键Id
		//可以用自增
	}
}
