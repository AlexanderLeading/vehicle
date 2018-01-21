package com.alexander.v.gateway.downstream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.alexander.v.gateway.model.Data;
import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;

public class DownStreamDataThread extends Thread {

	private static Logger logger = Logger.getLogger(DownStreamDataThread.class);

	private static BlockingQueue<Data> downQueue = new LinkedBlockingQueue<>();

	private long msgCount = 0;

	// 应答报文处理任务 线程池
	final ExecutorService cachedThreadPool = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors() * 2,
			new NamedThreadFactory("DownStreamThreadPool"));

	// 线程是否已经启动
	private boolean isStarted = false;

	public DownStreamDataThread() {

	}

	public DownStreamDataThread(String threadName) {
		this.setName(threadName);

	}

	public static void putData(Data msg) {
		downQueue.add(msg);
	}

	public void init() {
		try {
			if (!isStarted) {
				this.start();
				isStarted = true;
			}
		} catch (Exception e) {
			logger.error("DownStreamDataThread Error", e);
		}
	}

	@Override
	public void run() {
		try {
			Data dsd;
			int batchSize = 0;
			for (;;) {
				// 从下行的队列中获取数据，如果队列中有数据的话，那么就创建任务放到线程池中去执行
				if ((dsd = downQueue.take()) != null) {
					cachedThreadPool.execute(new DownStreamDataTask(dsd));
					msgCount++;
					batchSize++;
				}
				if (batchSize > 100) {
					Thread.sleep(1);
					batchSize = 0;
				}
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
