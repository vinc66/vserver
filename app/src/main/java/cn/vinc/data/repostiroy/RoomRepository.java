package cn.vinc.data.repostiroy;

import cn.vinc.domain.Room;
import cn.vinc.domain.TextMsg;
import cn.vinc.domain.User;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by vinc on 2017/11/24.
 */
@Component
public class RoomRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;


    public static final String ROOM_LIST_PREFIX = "cool:list:";
    public static final String ROOM_PREFIX = "cool:room:";
    public static final String ROOM_MSG_PREFIX = "cool:room:msg:";
    public static final String ROOM_USER_PREFIX = "cool:room:user:";

    public static final Integer DEFAULT_OLD = 10;

    public void set(Room room) {
        redisTemplate.opsForValue().set(ROOM_PREFIX + room.getRid(), JSON.toJSONString(room));
    }

    public Room get(int id) {
        return JSON.parseObject(redisTemplate.opsForValue().get(ROOM_PREFIX + id), Room.class);
    }

    public void msgSet(TextMsg textMsg) {
        redisTemplate.opsForList().leftPush(ROOM_MSG_PREFIX + textMsg.getRid(), JSON.toJSONString(textMsg));
    }

    public List<String> msgGet(int rrid) {
        return redisTemplate.opsForList().range(ROOM_MSG_PREFIX + rrid, 0, DEFAULT_OLD);
    }

    public void addUser(int rrid, User user) {
        redisTemplate.opsForZSet().add(ROOM_USER_PREFIX + rrid, JSON.toJSONString(user), user.getId());
    }

    public Set<String> listUser(int rrid) {
        return redisTemplate.opsForZSet().range(ROOM_USER_PREFIX + rrid, 0, -1);
    }

    public Set<String> list() {
        return redisTemplate.opsForZSet().range(ROOM_LIST_PREFIX, 0, -1);
    }

    public void add2List(int rid, String rname) {
        redisTemplate.opsForZSet().add(ROOM_LIST_PREFIX, rid + "_" + rname, rid);
    }
}
