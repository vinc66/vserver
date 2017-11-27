package cn.vinc.web.interceptor;

import cn.vinc.core.common.Constants;
import cn.vinc.core.support.CookieUtil;
import cn.vinc.core.support.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
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
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String uid = CookieUtil.getUid(servletRequest, Constants.ACCOUNT_ID);
        String rid = servletRequest.getParameter("rid");
        if (StringUtils.isBlank(uid) || StringUtils.isBlank(rid))
            return false;
        attributes.put("uid", IDGenerator.generate(uid));
        attributes.put("rid", rid);
        return true;
//        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info("------afterHandshake------");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
