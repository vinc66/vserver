package cn.vinc.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/27.
 */
@Getter
@Setter
public class TextMsg {
    private String uname;
    private Integer rid;
    private String msg;
    private String date;
    private Integer uid;
    private Integer gender;
    private Integer oper; //0 msg 1 con 2 discon

}
