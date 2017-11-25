package cn.vinc.web.controller;


import cn.vinc.data.repostiroy.UserRepository;
import cn.vinc.domain.User;
import cn.vinc.service.UserService;
import cn.vinc.web.vo.ResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by vinc on 2016/9/13.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/get")
    @ResponseBody
    public ResVo get() {
        User first = userRepository.get(1);
        if (first == null) {
            first = userService.getFirst(1);
            userRepository.set(first);
        }
        return new ResVo<>().setData(first);
    }
}
