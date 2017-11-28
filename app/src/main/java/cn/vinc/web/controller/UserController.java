package cn.vinc.web.controller;


import cn.vinc.core.common.Constants;
import cn.vinc.core.support.CookieUtil;
import cn.vinc.core.support.IDGenerator;
import cn.vinc.data.repostiroy.UserRepository;
import cn.vinc.domain.Room;
import cn.vinc.domain.User;
import cn.vinc.web.vo.ResVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by vinc on 2016/9/13.
 */
@Slf4j
@Controller
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/auth")
    @ResponseBody
    public ResVo auth(HttpServletRequest request, HttpServletResponse response, @Param("name") String name,@Param("gender") Integer gender) {
        String uid = CookieUtil.getUid(request, Constants.ACCOUNT_ID);
        if (StringUtils.isBlank(uid)) {
            if (StringUtils.isBlank(name) || gender == null)
                return new ResVo().buildError(-1);
            uid = IDGenerator.generate();
            CookieUtil.addCookie(response, Constants.ACCOUNT_ID, uid, -1);
            User user = new User();
            int generate = IDGenerator.generate(uid);
            System.out.println(generate);
            user.setId(generate);
            user.setName(name);
            user.setGender(gender);
            userRepository.set(user);
        }
        int generate = IDGenerator.generate(uid);
        User user = userRepository.get(generate);
        if (user == null) {
            CookieUtil.removeCookie(response, uid);
            return new ResVo().buildError(-1);
        }
        return new ResVo().setData(user);
    }
}
