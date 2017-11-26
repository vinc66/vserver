package cn.vinc.web.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/24.
 */
@Slf4j
public class WebSocketEndPoint extends TextWebSocketHandler {


    private static final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList());

    /**
     * 建立连接后
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("---------建立连接后afterConnectionEstablished--------members :{}", sessions.size());
        super.afterConnectionEstablished(session);
    }

    /**
     * 处理字符信息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("---------处理信息handleTextMessage--------{}", message.getPayload());
        super.handleTextMessage(session, message);
        for (WebSocketSession ses : sessions) {
            ses.sendMessage(new TextMessage(message.getPayload() + " ____ " + ses.getId()));
        }
    }


    /**
     * 异常处理
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("---------异常处理handleTransportError--------");
        super.handleTransportError(session, exception);
    }

    /**
     * 链接关闭
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("---------链接关闭-------- members :{}", sessions.size());
        super.afterConnectionClosed(session, status);
    }


    @Override
    public boolean supportsPartialMessages() {
        log.info("---------supportsPartialMessages--------");
        return super.supportsPartialMessages();
    }
}
