package com.alexander.v.gateway.upstream;

import java.util.Map;

import org.apache.log4j.Logger;

import com.accp.v.gateway.downstream.DownStreamDataThread;
import com.accp.v.gateway.kafka.ProducerPool;
import com.accp.v.gateway.kafka.TopicObject;
import com.accp.v.gateway.model.CommandTag;
import com.accp.v.gateway.model.Data;
import com.accp.v.gateway.netty.NettyChannelManager;
import com.accp.v.gateway.utils.JsonUtils;
import com.accp.v.gateway.utils.SpringContextUtils;

public class UpStreamDataTask implements Runnable {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(UpStreamDataTask.class);

	/**
	 * topic缓存map
	 */
	private static Map<String, TopicObject> topicMap = null;

	/**
	 * kafka 消息提供者对象池
	 */
	final ProducerPool producerPool;

	private Data data;

	private static final String TOPIC_NAME_LOGIN = "login";

	private static final String TOPIC_NAME_HEARTBEAT = "heartbeat";
	
	private static final String TOPIC_NAME_REALINFO = "realinfo";

	/**
	 * @author wenjun.jia
	 */
	public UpStreamDataTask(Data data) {
		this.data = data;
		
		//通过Spring获取Kafka
		producerPool = SpringContextUtils.getBean("producerPool");
	}

	/**
     *
     */
	@Override
	public void run() {
		if (data == null)
			return;
		String content = data.getContent();

		// 获取数据的命令单元
		int command = data.getCommandTag().getV();

		if (CommandTag.VEHICLE_LOGIN.getV() == command) {
			// 接受登陆报文的时候，需要把VIN和通道的Id放到缓存中(为了下发命令使用)
			NettyChannelManager.getInstance().bindUidAndChannel(data.getVin(),
					data.getChannel().id().asLongText());

			// 重新创建一个数据对象
			Data resultData = new Data();
			resultData.setCommandTag(data.getCommandTag()); // 复制数据的命令标示
			resultData.setVin(data.getVin()); // 复制数据的vin
			DownStreamDataThread.putData(resultData); // 把数据丢到下行线程的队列中
			
			resultData.setContent(data.getContent());
			String kafkaData = JsonUtils.toJSONString(resultData);

			// 往Kafka中发送数据
			//producerPool.send(TOPIC_NAME_LOGIN, data.getVin(), kafkaData);
		} else if (CommandTag.HEARTBEAT.getV() == command) {
			// 判断命了单元是心跳

			// 把系统时间的秒记录下来，作为数据内容(因为心跳默认发送的是空数据包，后台需要记录数据的接口时间)
			String json = "{\"collectTime\":" + System.currentTimeMillis()
					/ 1000 + "}";

			Data resultData = new Data();
			resultData.setCommandTag(data.getCommandTag()); // 复制数据的命令标示
			resultData.setVin(data.getVin()); // 复制数据的vin
			resultData.setContent(json); // 设计数据的内容

			// 把Data对象转成字符串
			String kafkaData = JsonUtils.toJSONString(resultData);
			
			// 往Kafka中发送数据
			//producerPool.send(TOPIC_NAME_HEARTBEAT, data.getVin(), kafkaData);
		} else if(CommandTag.REALTIME_INFO_REPORT.getV() == command){ //车辆实时数据处理
			Data realDate = new Data();// 新data,这个data就是要发送实时数据的data
			realDate.setCommandTag(data.getCommandTag());// 设置数据的命令单元
			realDate.setVin(data.getVin());// 车架号
			realDate.setContent(data.getContent());// 协议的内容,也就是我模拟的数据,把模拟的数据放入到这个专门用来发送实时数据的data中
			// 把data对象转换成字符串,准备发送给kafka
			String kafkaReportData = JsonUtils.toJSONString(realDate);
			// 往kafka发中发送数据,
			//producerPool.send(TOPIC_NAME_REALINFO, data.getVin(),
			//		kafkaReportData);
			
		}
	}
}
