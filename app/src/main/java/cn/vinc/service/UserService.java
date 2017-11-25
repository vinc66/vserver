package cn.vinc.service;

import cn.vinc.data.mapper.UserMapper;
import cn.vinc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vinc on 2016/9/19.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getFirst(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
