package com.alexander.v.tbox.downstream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.accp.v.tbox.model.Data;
import com.accp.v.tbox.netty.NettySocketClient;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;

public class RespondDataThread extends Thread {

	private static Logger logger = Logger.getLogger(RespondDataThread.class);

	/**
	 * 存放应答报文的队列
	 */
	private BlockingQueue<Data> responseQueue = new LinkedBlockingQueue<Data>();

	// 应答报文处理任务 线程池
	final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors(), new NamedThreadFactory(
					"RespondDataThreadPool"));

	// 线程是否已经启动
	private boolean isStarted = false;

	// 客户端对象
	private NettySocketClient nettySocketClient;

	public RespondDataThread() {

	}

	public RespondDataThread(String threadName,
			NettySocketClient nettySocketClient) {
		this.setName(threadName);
		this.nettySocketClient = nettySocketClient;
	}

	public NettySocketClient getNettySocketClient() {
		return nettySocketClient;
	}

	public void put(Data msg) {
		responseQueue.add(msg);
	}

	/**
	 * 
	 * @Description: 回复报文的启动方法
	 *
	 * @date 2017年7月5日,下午9:40:45
	 * @author chenyun
	 * @version 1.0.0
	 *
	 */
	public void init() {
		try {
			if (!isStarted) {
				start();
				isStarted = true;
			}
		} catch (Exception e) {
			logger.error("RespondDataThread Error", e);
		}
	}

	@Override
	public void run() {
		try {
			logger.info("[Client(" + this.nettySocketClient.getClientId()
					+ ")][下行数据线程启动...]");
			Data data;
			while (true) {
				// 循环从队列里拉取回复报文,如果有回复报文，那么就创建一个单独的任务线程，然后丢到线程池去处理
				if ((data = responseQueue.take()) != null) {
					fixedThreadPool.execute(new RespondDataTask(
							nettySocketClient, data));
					Thread.sleep(2l);
				}
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
