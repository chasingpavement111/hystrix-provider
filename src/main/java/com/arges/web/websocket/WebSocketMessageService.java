package com.arges.web.websocket;

import java.util.List;

/**
 * @author zhangjie
 */
public interface WebSocketMessageService {
    /**
     * 根据用户类型，广播学校报警总数：
     * 1、普通学校用户：接受本学校的报警总数更新数量
     * 2、教育局管理员用户：接受所有学校的报警总数更新数量
     */
    void sendMsgOfAlarmCount(List<Long> businessIdList);
}
