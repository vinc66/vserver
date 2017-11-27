package cn.vinc.web.endpoint;

import cn.vinc.data.repostiroy.RoomRepository;
import cn.vinc.data.repostiroy.UserRepository;
import cn.vinc.domain.Operation;
import cn.vinc.domain.Room;
import cn.vinc.domain.User;
import cn.vinc.domain.TextMsg;
import cn.vinc.web.vo.UserList;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/24.
 */
@Slf4j
public class WebSocketEndPoint extends TextWebSocketHandler {


//        private static final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList());

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
        Object rid = session.getAttributes().get("rid");
        if (rid == null)
            return;
        Object uid = session.getAttributes().get("uid");
        if (uid == null)
            return;

        int rrid = NumberUtils.toInt(rid.toString());
        int uuid = NumberUtils.toInt(uid.toString());
        Room room = roomRepository.get(rrid);
        User user = userRepository.get(uuid);
        if (room == null || user == null)
            return;
        if (!sessions.containsKey(rrid)) {
            Set<WebSocketSession> webSocketSessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
            sessions.put(rrid, webSocketSessions);
        }
        session.getAttributes().put("uname", user.getName());
        Set<WebSocketSession> webSocketSessions = sessions.get(rrid);
        webSocketSessions.add(session);

        TextMsg textMsg = new TextMsg();
        textMsg.setRid(rrid);
        textMsg.setUname(user.getName());
        textMsg.setUid(uuid);
        textMsg.setGender(user.getGender());
        textMsg.setMsg("comming");
        textMsg.setDate(new Date().toString());
        textMsg.setOper(Operation.CONNECTION.value);
        String jsonString = JSON.toJSONString(textMsg);
        roomRepository.msgSet(textMsg);
        roomRepository.addUser(rrid,user);

        for (WebSocketSession ses : webSocketSessions) {
            ses.sendMessage(new TextMessage(jsonString));
        }
        log.info("---------建立连接后afterConnectionEstablished-----rid :{}---members :{}", rid, sessions.size());
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
        Object rid = session.getAttributes().get("rid");
        if (rid == null)
            return;
        Object uid = session.getAttributes().get("uid");
        if (uid == null)
            return;

        int rrid = NumberUtils.toInt(rid.toString());
        int uuid = NumberUtils.toInt(uid.toString());
        User user = userRepository.get(uuid);

        TextMsg textMsg = new TextMsg();
        textMsg.setRid(rrid);
        textMsg.setUname(user.getName());
        textMsg.setUid(uuid);
        textMsg.setGender(user.getGender());
        textMsg.setMsg(message.getPayload());
        textMsg.setDate(new Date().toString());
        textMsg.setOper(Operation.MSG.value);
        String jsonString = JSON.toJSONString(textMsg);
        roomRepository.msgSet(textMsg);

        Set<WebSocketSession> webSocketSessions = sessions.get(rrid);
        for (WebSocketSession ses : webSocketSessions) {
            ses.sendMessage(new TextMessage(jsonString));
        }
        super.handleTextMessage(session, message);
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
        Object rid = session.getAttributes().get("rid");
        if (rid == null)
            return;
        Object uid = session.getAttributes().get("uid");
        if (uid == null)
            return;
        int rrid = NumberUtils.toInt(rid.toString());
        int uuid = NumberUtils.toInt(uid.toString());
        Room room = roomRepository.get(rrid);
        User user = userRepository.get(uuid);
        if (room == null || user == null)
            return;
        if (!sessions.containsKey(rrid)) {
            Set<WebSocketSession> webSocketSessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
            sessions.put(rrid, webSocketSessions);
        }
        TextMsg textMsg = new TextMsg();
        textMsg.setRid(rrid);
        textMsg.setUname("system");
        textMsg.setUid(uuid);
        textMsg.setGender(user.getGender());
        textMsg.setMsg(user.getName() + " left....");
        textMsg.setDate(new Date().toString());
        textMsg.setOper(Operation.DISCONNECTION.value);
        String jsonString = JSON.toJSONString(textMsg);
        roomRepository.msgSet(textMsg);

        roomRepository.removeUser(rrid,user);
        Set<WebSocketSession> webSocketSessions = sessions.get(rrid);
        webSocketSessions.remove(session);
        for (WebSocketSession ses : webSocketSessions) {
            ses.sendMessage(new TextMessage(jsonString));
        }
        log.info("---------关闭连接后afterConnectionClosed-----rid :{}---members :{}", rid, sessions.size());

        super.afterConnectionClosed(session, status);
    }


    @Override
    public boolean supportsPartialMessages() {
        log.info("---------supportsPartialMessages--------");
        return super.supportsPartialMessages();
    }
}
