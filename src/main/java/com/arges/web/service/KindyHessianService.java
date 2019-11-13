package com.arges.web.service;

import java.util.List;

/**
 * “爱看宝宝”项目的通知推送服务类
 *
 * @author zhangjie
 */
public interface KindyHessianService {

    /**
     * 推送消息给指定clientId的用户
     *
     * @param systemType   客户端应用系统类型（IOS=1，Android=2）
     * @param endpointType 客户端的业务类型（家长端=1，教师端=2）
     * @param clientIdList 推送目标用户唯一标识符
     * @param subject      消息主题
     * @param content      消息内容
     * @return 推送成功结果
     */
    boolean pushTransmission(int systemType, int endpointType, List<String> clientIdList, String subject, String content) throws Exception;
}
