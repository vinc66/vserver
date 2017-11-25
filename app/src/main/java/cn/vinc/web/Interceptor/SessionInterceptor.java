package cn.vinc.web.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by vinc on 2016/9/14.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
