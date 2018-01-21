package com.alexander.v.gateway.upstream;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.accp.v.gateway.model.Data;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;

/**
 * 
 * @ClassName: UpStreamDataThread 
 * @Description: 接收网关发送的主线程 
 * @author chenyun
 * @date 2017年7月6日 上午12:26:06 
 * 
 * @version 1.0.0
 */
public class UpStreamDataThread extends Thread {

	private static final Logger logger = Logger
			.getLogger(UpStreamDataThread.class);

	// 上报消息队列
	private static BlockingQueue<Data> upQueue = new LinkedBlockingQueue<>();
	// 处理的消息数量
	private long msgCount = 0;

	// 是否已启动
	private boolean isStarted = false;

	// 上报数据处理任务 线程池
	final ExecutorService cachedThreadPool = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors() * 2,
			new NamedThreadFactory("UpStreamThreadPool"));

	public UpStreamDataThread(String threadName) {
		this.setName(threadName);

	}

	public static void put(Data data) {
		upQueue.add(data);
	}

	public static void put(List<Data> dataList) {
		for (Data data : dataList) {
			upQueue.add(data);
		}
	}

	public void init() {
		try {
			if (!isStarted) {
				this.start();
				isStarted = true;
			}
		} catch (Exception e) {
			logger.error("UpStreamDataThread Error", e);
		}
	}

	@Override
	public void run() {
		Data usd;
		try {
			for (;;) {
				// 从上行的队列中获取上行数据，然后创建上行任务，放到线程池去执行
				if ((usd = upQueue.take()) != null) {
					cachedThreadPool.execute(new UpStreamDataTask(usd));
					msgCount++;
				}
				Thread.sleep(0);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @Description: 把数据添加到上行队列中
	 *
	 * @date 2017年7月6日,上午12:25:37
	 * @author chenyun
	 * @version 1.0.0
	 *
	 * @param data
	 */
	public static void putUpStreamDataFromServerHandler(Data data) {
		if (data == null)
			return;
		put(data);
	}
}
