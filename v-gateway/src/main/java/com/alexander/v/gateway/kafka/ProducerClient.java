package com.alexander.v.gateway.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerClient {

	private Producer<String, byte[]> serverProducer;

	private Properties props;

	/**
	 * 初始化kafka提供者
	 */
	@SuppressWarnings("unused")
	private void init() {
		ProducerConfig config = new ProducerConfig(props);
		serverProducer = new Producer<>(config);
	}

	/**
	 * 发送掉线ICU的UID给转发网关处理
	 * 
	 * */
	public void send(String topic, String key, String content) throws Exception {
		KeyedMessage<String, byte[]> msg = new KeyedMessage<>(topic, key,
				content.getBytes());
		serverProducer.send(msg);
	}

	/**
	 *
	 * @return the props
	 */

	public Properties getProps() {
		return props;
	}

	/**
	 *
	 * @param props
	 *            the props to set
	 */
	public void setProps(Properties props) {
		this.props = props;
	}
}
