package cn.vinc.web.support;

/**
 * Created by vinc on 2016/9/21.
 */
public class LoginTokenHolder {

    private static ThreadLocal<Integer> t = new ThreadLocal<>();
    public static void setAccountId(Integer accountId) {
        t.set(accountId);
    }
    public static Integer getAccountId() {
        return t.get();
    }
    public static void clear() {
        t.remove();
    }


}
