package cn.vinc.web.vo;


import cn.vinc.core.common.Constants;

/**
 * Created by vinc on 2016/9/13.
 */
public class ResVo<T> {

    private T res;

    private int error_code;

    private String error_msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }


    public ResVo() {
        error_code = Constants.SUCCESS;
    }

    public ResVo setData(T data) {
        if (data != null) {
            error_code = Constants.SUCCESS;
        } else {
            error_code = Constants.FAIL;
        }
        this.res = data;
        return this;
    }

    public ResVo buildError(int fail) {
        this.error_code = fail;
        return this;
    }


}
