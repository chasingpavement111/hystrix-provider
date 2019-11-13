package com.arges.web.websocket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.arges.web.vo.ResultData;

/**
 * @author zhangjie
 */
@Service
public class WebSocketMessageServiceImpl implements WebSocketMessageService {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketMessageServiceImpl.class);

    /**
     * 教育局用户的监听businessId值
     * 因为教育局用户无所属学校，所以需要设置默认值
     */
    private final static String TOPIC_DEFAULT_BUSINESS_ID_FOR_ADMIN_USER = "0";

    /**
     * 利用socket连接，向监听指定topic的客户端发生消息
     * 没有用
     */
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketMessageServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendMsgOfAlarmCount(List<Long> businessIdList) {
        List<Map<String, Object>> alarmCountListFromDB = new ArrayList<>();//businessUserService.getAlarmCountInDB(businessIdList);
        Map<String, CustomSocketMessage> msgMap = buildMsgForAlarmCount(businessIdList, alarmCountListFromDB);
        // 向通道所属学校的用户和教育局的用户发送消息
        msgMap.forEach((destination, msg) -> {
            ResultData responseDTO = new ResultData();
            responseDTO.setResult_code(0);
            responseDTO.setResult_desc("成功");
            responseDTO.setResult_data(msg);
            simpMessagingTemplate.convertAndSend(destination, responseDTO);
            logger.trace("成功发送消息，目的地址为：" + destination);
        });
    }


    /**
     * 日期转换 时间戳String -> Date
     *
     * @param timeDiff
     * @return
     */
    private Date getDate(String timeDiff) {
        Instant instant = Instant.now();
        Long timeLong = Long.valueOf(timeDiff);
        if (timeDiff.length() == 10) {
            instant = Instant.ofEpochSecond(timeLong);
        } else if (timeDiff.length() == 13) {
            instant = Instant.ofEpochMilli(timeLong);
        }

        return new Date(instant.toEpochMilli());
    }

    private Map<String, CustomSocketMessage> buildMsgForAlarmCount(List<Long> businessIdList, List<Map<String, Object>> alarmCountList) {
        // 构造待发送消息（destination对msg 是多对多的关系）
        Map<String, CustomSocketMessage> msgMap = new HashMap<>();
        CustomSocketMessage baseMsg = new CustomSocketMessage();
//        baseMsg.setEventType(Constant.EVENT_NAME_OF_ALARM_COUNT_FROM_SCHEDULER);
        baseMsg.setEventType("Constant.EVENT_NAME_OF_ALARM_COUNT_FROM_SCHEDULER");
        baseMsg.setIssuedTime(new Date());
        // 教育局用户
        {
            // 构造发送给教育局用户的msg消息体：包含所有学校的alarmCount数据
            CustomSocketMessage msg = new CustomSocketMessage();
            BeanUtils.copyProperties(baseMsg, msg);
            msg.setBusinessId(0L);//教育局用户的默认学校id值
            msg.setList(alarmCountList);
//            String destination = Constant.WEBSOCKET_TOPIC_NAME_FOR_SCHOOL_UPDATES + "/" + TOPIC_DEFAULT_BUSINESS_ID_FOR_ADMIN_USER;
            String destination = "";
            msgMap.put(destination, msg);
        }
        if (businessIdList == null) {
            return msgMap;
        }
        // 学校用户
        Map<String, Map<String, Object>> alarmCountMap = new HashMap<>();
        alarmCountList.forEach(map -> alarmCountMap.put(map.get("businessId").toString(), map));
        businessIdList.forEach(businessId -> {
            // 构造发送给学校用户的msg消息体：仅包含所属学校的alarmCount数据
            CustomSocketMessage msg = new CustomSocketMessage();
            BeanUtils.copyProperties(baseMsg, msg);
            msg.setBusinessId(businessId);
            msg.setList(Collections.singletonList(alarmCountMap.get(businessId.toString())));
//            String destination = Constant.WEBSOCKET_TOPIC_NAME_FOR_SCHOOL_UPDATES + "/" + businessId;
            //            String destination = Constant.WEBSOCKET_TOPIC_NAME_FOR_SCHOOL_UPDATES + "/" + TOPIC_DEFAULT_BUSINESS_ID_FOR_ADMIN_USER;
            String destination = "";
            msgMap.put(destination, msg);
        });
        return msgMap;
    }
}
