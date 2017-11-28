package cn.vinc.web.endpoint;

import cn.vinc.data.repostiroy.RoomRepository;
import cn.vinc.data.repostiroy.UserRepository;
import cn.vinc.domain.Operation;
import cn.vinc.domain.Room;
import cn.vinc.domain.User;
import cn.vinc.domain.TextMsg;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/24.
 */
@Slf4j
public class WebSocketEndPoint extends TextWebSocketHandler {

    private static final Map<Integer, Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();


    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 建立连接后
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        TextMsg textMsg = new TextMsg();
        textMsg.setOper(Operation.CONNECTION.value);
        int roomcount = handlerSession(session, textMsg);
        log.info("---------建立连接后afterConnectionEstablished-----rid :{}---members :{}", textMsg.getRid(), roomcount);
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
        TextMsg textMsg = new TextMsg();
        textMsg.setMsg(message.getPayload());
        textMsg.setOper(Operation.MSG.value);
        handlerSession(session,textMsg);
        super.handleTextMessage(session, message);
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

        TextMsg textMsg = new TextMsg();
        textMsg.setOper(Operation.DISCONNECTION.value);
        int roomcount = handlerSession(session, textMsg);
        log.info("---------关闭连接后afterConnectionClosed-----rid :{}---members :{}", textMsg.getRid(), roomcount);
        super.afterConnectionClosed(session, status);
    }

    private int handlerSession(WebSocketSession session, TextMsg textMsg) throws Exception {
        Object rid = session.getAttributes().get("rid");
        Object uid = session.getAttributes().get("uid");
        int rrid = NumberUtils.toInt(rid.toString());
        int uuid = NumberUtils.toInt(uid.toString());
        Room room = roomRepository.get(rrid);
        User user = userRepository.get(uuid);
        if (room == null || user == null) {
            log.info("room or user not exist ---- rid :{} , uid :{}", rid, uid);
            return 0;
        }
        if (!sessions.containsKey(rrid)) {
            Set<WebSocketSession> webSocketSessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
            sessions.put(rrid, webSocketSessions);
        }
        textMsg.setDate(System.currentTimeMillis());
        textMsg.setRid(rrid);
        textMsg.setUid(uuid);
        textMsg.setUname(user.getName());
        textMsg.setGender(user.getGender());
        Set<WebSocketSession> webSocketSessions = sessions.get(rrid);
        switch (textMsg.getOper()) {
            case 1:
                textMsg.setUList(roomRepository.listUser(rrid));
                webSocketSessions.add(session);
                roomRepository.addUser(rrid, user);
                break;
            case 2:
                textMsg.setUList(roomRepository.listUser(rrid));
                webSocketSessions.remove(session);
                break;
            case 0:
                roomRepository.msgSet(textMsg);
                break;
        }
        String jsonString = JSON.toJSONString(textMsg);
        for (WebSocketSession ses : webSocketSessions) {
            ses.sendMessage(new TextMessage(jsonString));
        }
        return webSocketSessions.size();
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
        log.error("websocket 处理异常 roomid :{} , uid :{}", session.getAttributes().get("rid"), session.getAttributes().get("uid"), exception);
        super.handleTransportError(session, exception);
    }

    @Override
    public boolean supportsPartialMessages() {
        log.info("---------supportsPartialMessages--------");
        return super.supportsPartialMessages();
    }
}
