package cn.vinc.web.controller;

import cn.vinc.core.common.Constants;
import cn.vinc.core.support.CookieUtil;
import cn.vinc.core.support.IDGenerator;
import cn.vinc.data.repostiroy.RoomRepository;
import cn.vinc.domain.Room;
import cn.vinc.web.vo.ResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/27.
 */
@Slf4j
@Controller
@RequestMapping("api/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    /**
     * 创建房间
     * @param request
     * @param name
     * @return
     */
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
        roomRepository.add2List(room.getRid(), room.getRname());
        return new ResVo<>().setData(room);
    }

    /**
     * 获取房间列表
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResVo list() {
        return new ResVo<>().setData(roomRepository.list());
    }

    /**
     * 当前房间历史聊天记录
     * @param rid
     * @return
     */
    @RequestMapping(value = "/olds")
    @ResponseBody
    public ResVo olds(@Param("rid") String rid) {
        return new ResVo<>().setData(roomRepository.msgGet(NumberUtils.toInt(rid, 1)));
    }

    /**
     * 获取房间信息
     * @param rid
     * @return
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public ResVo get(@Param("rid") String rid) {
        Room room = roomRepository.get(NumberUtils.toInt(rid, 1));
        if (room == null) {
            room = roomRepository.get(1);
            if (room == null) {
                Room tmp = new Room();
                tmp.setCreaterId(1);
                tmp.setRid(1);
                tmp.setRname("hello world");
                roomRepository.set(tmp);
                roomRepository.add2List(tmp.getRid(), tmp.getRname());
                room = tmp;
            }
        }
        return new ResVo<>().setData(room);
    }

}
