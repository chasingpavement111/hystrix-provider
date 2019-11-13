package com.arges.web.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import sun.security.acl.PrincipalImpl;

/**
 * @author zhangjie
 */
public class WebSocketHandShakeInterceptor extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 设置 sendToUser 的 user 值
//		return super.determineUser(request, wsHandler, attributes);// 默认方法：NPE
        String sessionId = ((ServletServerHttpRequest) request).getServletRequest().getSession().getId();
        return new PrincipalImpl(sessionId);
    }
}
