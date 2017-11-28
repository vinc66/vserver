package cn.vinc.web.interceptor;

import cn.vinc.core.common.Constants;
import cn.vinc.core.support.CookieUtil;
import cn.vinc.web.vo.ResVo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;

/**
 * Created by vinc on 2016/9/14.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String uid = CookieUtil.getUid(request, Constants.ACCOUNT_ID);
        if(StringUtils.isBlank(uid)) {
            ServletOutputStream out = response.getOutputStream();
            IOUtils.write(JSON.toJSONString(new ResVo<>().buildError()), out, Constants.DEFAULT_CHARSET);
            response.setContentType(ContentType.APPLICATION_JSON.withCharset(Constants.DEFAULT_CHARSET).toString());
            try {
                out.flush();
            } finally {
                IOUtils.closeQuietly(out);
            }
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
