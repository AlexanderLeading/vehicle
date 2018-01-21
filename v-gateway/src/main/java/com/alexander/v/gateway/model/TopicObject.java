package com.alexander.v.gateway.model;

import java.io.Serializable;

public class TopicObject implements Serializable {
    
    /**
     * ID标识
     */
    private String id;
    /**
     * 父ID标识
     */
    private String pid;
    /**
     * 报文中命令标识
     */
    private String commandTag;
    /**
     * topic值
     */
    private String topic;
    /**
     * 描述
     */
    private String describe;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCommandTag() {
        return commandTag;
    }

    public void setCommandTag(String commandTag) {
        this.commandTag = commandTag;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
