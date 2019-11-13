package com.arges.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * @author zhangjie
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    Environment env;

    @Autowired
    private TaskScheduler taskScheduler;

    /**
     * 使用规范路径：（使用simple broker 没有实际意义，但是使用Stomp Broker时需要遵守规范）
     * app : 代表所有需要进入 MessageMapping方法的消息
     * topic : 代表用于一对多广播的消息
     * queue : 代表用于一对一会话的消息
     * secured : 需要身份认证的消息，需要带Authorization请求头信息
     * v1 : 版本v1使用的会话消息类型
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        long sx = Long.parseLong(env.getProperty("websocket.broker.sx"));
        long sy = Long.parseLong(env.getProperty("websocket.broker.sy"));
//		registry.enableStompBrokerRelay("/stomp/topic", "");// 适合分布式系统
        registry.enableSimpleBroker("/topic", "/queue")
                .setHeartbeatValue(new long[]{sx, sy})  // 服务端每5s向建立连接的客户端发送一次心跳，客户端发送心跳的间隔不可大于sy
                .setTaskScheduler(taskScheduler);//todo 计算用户量，避免资源吃不消。每次用户都发一遍（在一个定时任务中）
        registry.setApplicationDestinationPrefixes("/app/secured");
        registry.setUserDestinationPrefix("/user/");// @SendToUser的默认前缀
//		registry.setPreservePublishOrder(true);// 保证消息顺序：与发布顺序一致（顺序性会有一定的性能开销。因此默认使用多线程发生消息，不保证消息接受顺序）
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 不需要写入endpoint的路径 ：contextPath, web.xml 的servlet-mapping 中要求的 url-pattern(本项目为api)
        registry.addEndpoint("/socket");
        registry.addEndpoint("/socket")
                .setHandshakeHandler(handshakeInterceptor())
                .withSockJS()
                //设置sockjs 下载路径，最好与客户端使用的资源一致
                .setClientLibraryUrl("https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js");
    }

    /**
     * 用于传递从WebSocket客户端收到的消息
     *
     * @param registry
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registry) {
        registry.interceptors(inboundChannelInterceptor());
    }

    /**
     * 用于将服务器消息发送到WebSocket客户端
     *
     * @return
     */
//	@Override
//	public void configureClientOutboundChannel(ChannelRegistration registry)
//	{
//		registry.setInterceptors(outboundChannelInterceptor());
//	}

    /**
     * 日志记录和异常处理
     *
     * @return
     */
    //	@Override
//	public void configureWebSocketTransport(WebSocketTransportRegistration registration)
//	{
//		registration.addDecoratorFactory(CustomWebSocketHandlerDecorator::new);
//	}
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 99)// 需要确保身份验证拦截器在Spring Security之前配置。
    public ChannelInterceptor inboundChannelInterceptor() {
        return new WebSocketInboundSecurityInterceptor();
    }

    @Bean
    public DefaultHandshakeHandler handshakeInterceptor() {
        return new WebSocketHandShakeInterceptor();
    }
}