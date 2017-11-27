package cn.vinc.web.controller;

import cn.vinc.core.common.Constants;
import cn.vinc.core.support.CookieUtil;
import cn.vinc.core.support.IDGenerator;
import cn.vinc.data.repostiroy.RoomRepository;
import cn.vinc.data.repostiroy.UserRepository;
import cn.vinc.domain.Room;
import cn.vinc.domain.User;
import cn.vinc.web.vo.ResVo;
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
 * Created by wangaixu@chuchujie.com on 2017/11/27.
 */
@Slf4j
@Controller
@RequestMapping("api/room")
public class RoomController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping(value = "/create")
    @ResponseBody
    public ResVo create(HttpServletRequest request, String name) {
        String uid = CookieUtil.getUid(request, Constants.ACCOUNT_ID);
        if (StringUtils.isBlank(uid) || StringUtils.isBlank(name))
            return new ResVo().buildError();

        int generate = IDGenerator.generate(uid);
        Room room = new Room();
        room.setCreaterId(generate);
        room.setRid(IDGenerator.generateID());
        room.setRname(name);
        roomRepository.set(room);
        return new ResVo<>().setData(room);
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public ResVo list(HttpServletRequest request, String rid) {
        String uid = CookieUtil.getUid(request, Constants.ACCOUNT_ID);
        if (StringUtils.isBlank(rid) )
            return new ResVo().buildError();

        return new ResVo<>().setData(null);
    }

}
