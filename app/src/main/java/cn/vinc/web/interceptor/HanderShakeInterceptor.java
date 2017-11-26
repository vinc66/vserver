package cn.vinc.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Collection;
import java.util.Map;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/24.
 */
@Slf4j
public class HanderShakeInterceptor extends HttpSessionHandshakeInterceptor {

    public HanderShakeInterceptor() {
    }

    public HanderShakeInterceptor(Collection<String> attributeNames) {
        super(attributeNames);
    }

    @Override
    public Collection<String> getAttributeNames() {
        return super.getAttributeNames();
    }

    @Override
    public void setCopyAllAttributes(boolean copyAllAttributes) {
        super.setCopyAllAttributes(copyAllAttributes);
    }

    @Override
    public boolean isCopyAllAttributes() {
        return super.isCopyAllAttributes();
    }

    @Override
    public void setCopyHttpSessionId(boolean copyHttpSessionId) {
        super.setCopyHttpSessionId(copyHttpSessionId);
    }

    @Override
    public boolean isCopyHttpSessionId() {
        return super.isCopyHttpSessionId();
    }

    @Override
    public void setCreateSession(boolean createSession) {
        super.setCreateSession(createSession);
    }

    @Override
    public boolean isCreateSession() {
        return super.isCreateSession();
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("------beforeHandshake------");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info("------afterHandshake------");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
