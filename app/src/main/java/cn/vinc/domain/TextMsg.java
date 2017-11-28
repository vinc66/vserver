package cn.vinc.domain;

import cn.vinc.core.support.DateFormateUtil;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Set;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/27.
 */
@Getter
@Setter
public class TextMsg {
    private String uname;
    private Integer rid;
    private String msg;
    private Long date;
    private String dateStr;
    private Integer uid;
    private Integer gender;
    private Integer oper; //0 msg 1 con 2 discon
    private Set<String> uList;

    public String getDateStr() {
        return DateFormateUtil.formate(date);
    }


}
