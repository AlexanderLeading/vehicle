package com.alexander.v.gateway.netty;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class NettyChannelManager {

	private static final Logger logger = Logger
			.getLogger(NettyChannelManager.class);

	/**
	 * 存放ChannelId与Channel Map
	 */
	private final ConcurrentHashMap<String, Channel> activeChannelMap = new ConcurrentHashMap<>(
			20000);
	/**
	 * 存放Uid与ChannelId对应关系
	 */
	private final ConcurrentHashMap<String, String> uidChannelIdMap = new ConcurrentHashMap<>(
			20000);

	public static NettyChannelManager getInstance() {
		return NettyChannelManagerHolder.instance;
	}

	private static class NettyChannelManagerHolder {
		private static NettyChannelManager instance = new NettyChannelManager();

		private NettyChannelManagerHolder() {
		}
	}

	/**
	 * 根据Uid获取Channel
	 * 
	 * @param uid
	 */
	public Channel getChannelByUid(String uid) {
		if (uid == null || "".equals(uid))
			return null;
		String channelId = uidChannelIdMap.get(uid);
		if (channelId == null || "".equals(channelId))
			return null;
		return activeChannelMap.get(channelId);
	}

	/**
	 * 绑定 Uid与ChannelId的关联关系
	 * 
	 * @param uid
	 * @param channelId
	 */
	public void bindUidAndChannel(String uid, String channelId) {
		if (uid == null || "".equals(uid))
			return;
		if (channelId == null || "".equals(channelId))
			return;
		uidChannelIdMap.put(uid, channelId);
	}

	/**
	 * 删除通道
	 * 
	 * @param channelId
	 */
	public void removeChannel(Channel channel) {
		if (channel == null)
			return;
		String channelId = channel.id().asLongText();
		Channel channelOld = this.activeChannelMap.remove(channelId);
		if (channelOld == null)
			return;
		channel.close();
	}

	/**
	 * 添加通道
	 * 
	 * @param channelId
	 */
	public void putChannel(Channel channel) {
		this.activeChannelMap.putIfAbsent(channel.id().asLongText(), channel);
	}
}
