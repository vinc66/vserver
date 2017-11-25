package cn.vinc.data.repostiroy;

import cn.vinc.domain.User;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by vinc on 2017/11/24.
 */
@Component
public class UserRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;


    public static final String USER_PREFIX = "cool:user:";

    public void set(User user) {
        redisTemplate.opsForValue().set(USER_PREFIX + user.getId(), JSON.toJSONString(user));
    }

    public User get(int id) {
        return JSON.parseObject(redisTemplate.opsForValue().get(USER_PREFIX + id), User.class);
    }
}
