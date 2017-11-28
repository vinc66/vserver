package cn.vinc.core.support;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/28.
 */
public class DateFormateUtil {

    public static String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat simpleDateFormat;

    static {
        simpleDateFormat = new SimpleDateFormat(YY_MM_DD_HH_MM_SS);
    }

    public static String formate(Long date) {
        return simpleDateFormat.format(new Date(date));
    }
}
