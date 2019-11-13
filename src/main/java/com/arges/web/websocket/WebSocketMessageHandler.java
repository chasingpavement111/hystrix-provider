package com.arges.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhangjie
 */
@Controller
public class WebSocketMessageHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketMessageHandler.class);

    @Autowired
    private WebSocketMessageService webSocketMessageService;

    /**
     * 统一处理：来自@MessageMapping方法的异常。修饰@Controller的方法
     * 也可以使用@ControllerAdvice 修饰的异常统一处理类，进行处理
     * 不会捕获拦截器中的报错
     *
     * @param e
     * @return
     */
//	@MessageExceptionHandler
//	@SendToUser(destinations = Constant.WEBSOCKET_QUEUE_NAME_FOR_ERRORS, broadcast = false)// 捕获的报错消息不广播，只发送给对应用户：broadcast = false
//	public JsonResult handleException(Exception e)
//	{
//		e.printStackTrace();
//		JsonResult jsonResult = new JsonResult();
//		jsonResult.setResult(ErrorCodeEnum.FAIL);
//		jsonResult.setResult_desc(e.getMessage());
//		return jsonResult;
//	}
//
//	/**
//	 * SendToUser(broadcast = false) : 实现websocket 一对一对话，不会广播给所有使用该账号登陆的用户
//	 * broadcast=true 时，会向所有用户(所有建立了连接的用户，与登陆账号无关)发送消息
//	 * 保证value值在所有MessageMapping值中唯一
//	 *
//	 * @param message
//	 * @param businessId
//	 * @return
//	 */
//	@MessageMapping(Constant.WEBSOCKET_DESTINATION_FOR_UPDATES + "/{businessId}")
//	@SendToUser(value = Constant.WEBSOCKET_QUEUE_NAME_FOR_UPDATES, broadcast = false)
//	public JsonResult sendAlarmCount(CustomSocketMessage message, @DestinationVariable String businessId)
//	{//todo 用于测试
//		CustomSocketMessage rtnMessage = webSocketMessageService.getAlarmCountForTest(null, Long.valueOf(businessId));
//		rtnMessage.setEventType(message.getEventType());
//		return new JsonResult(rtnMessage);
//	}
//
//	@MessageMapping(Constant.WEBSOCKET_DESTINATION_FOR_UPDATES)
//	@SendToUser(value = Constant.WEBSOCKET_QUEUE_NAME_FOR_UPDATES, broadcast = false)
//	public JsonResult sendUserAlarmCount(@Payload CustomSocketMessage message, Principal user, @Header("simpSessionId") String sessionId)
//	{// todo 测试报错消息
//		if (message.getEventType().matches("[0-9]+"))
//		{
//			throw new RuntimeException("msgType 包含数字，抛异常");
//		}
//		logger.info("user identifier = " + user.getName() + ", sessionId = " + sessionId);
//		return new JsonResult(message);
//	}
}
