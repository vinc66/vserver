package cn.vinc.domain;

/**
 * Created by wangaixu@chuchujie.com on 2017/11/27.
 */
public enum  Operation {

    MSG(0),CONNECTION(1),DISCONNECTION(2);

    public final int value;

    Operation(int i) {
        this.value = i;
    }
}
