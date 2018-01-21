package com.alexander.v.tbox.upstream;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.accp.v.tbox.model.Data;
import com.accp.v.tbox.netty.NettySocketClient;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;

public class ReportDataThread extends Thread {

	private static final Logger logger = Logger
			.getLogger(ReportDataThread.class);

	// 上报消息队列
	private static BlockingQueue<Data> reportQueue = new LinkedBlockingQueue<Data>();

	// 客户端对象
	private NettySocketClient nettySocketClient = null;

	// 是否已启动
	private boolean isStarted = false;

	// 上报数据处理任务 线程池
	final ExecutorService executorThreadPool = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors() + 1,
			new NamedThreadFactory("ReportDataThreadPool"));

	public ReportDataThread(String threadName,
			NettySocketClient nettySocketClient) {
		this.setName(threadName);
		this.nettySocketClient = nettySocketClient;
	}

	public NettySocketClient getNettySocketClient() {
		return nettySocketClient;
	}

	public void put(Data msg) {
		reportQueue.add(msg);
	}

	public void put(List<Data> msg) {
		for (Data data : msg) {
			reportQueue.add(data);
		}
	}

	public void init() {
		try {
			if (!isStarted) {
				start();
				isStarted = true;
			}
		} catch (Exception e) {
			logger.error("ReportDataThread Error", e);
		}
	}

	@Override
	public void run() {
		logger.info("[Client(" + this.nettySocketClient.getClientId()
				+ ")][上行数据线程启动...]");
		Data data;
		try {
			for (;;) {
				
				if ((data = reportQueue.take()) != null) {
					executorThreadPool.execute(new ReportDataTask(
							nettySocketClient, data));
				}
				Thread.sleep(0l);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
