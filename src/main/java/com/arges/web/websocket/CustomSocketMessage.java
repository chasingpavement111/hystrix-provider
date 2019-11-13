package com.arges.web.websocket;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * websocket消息交换类
 *
 * @author zhangjie
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSocketMessage implements Serializable {
    private static final long serialVersionUID = 176717301088288437L;

    /**
     * 消息类型
     * 学校报警总数
     * 新增的报警信息
     * 实时的传感器信息
     */
    private String eventType;

    /**
     * 消息发布\上报时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issuedTime;

    /**
     * 推送目标用户的所属学校id
     */
    private Long businessId;

    /**
     * 推送数据
     */
    private List<Map<String, Object>> list;

    public String getEventType() {
        return eventType;
    }

    void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getIssuedTime() {
        return issuedTime;
    }

    void setIssuedTime(Date issuedTime) {
        this.issuedTime = issuedTime;
    }

    public Long getBusinessId() {
        return businessId;
    }

    void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
}
