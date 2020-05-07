package com.imooc.o2o.interceptor.shopadmin;

import com.imooc.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 店家管理系统登陆验证拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userObject = request.getSession().getAttribute("user");
        if (userObject != null) {
            PersonInfo user = (PersonInfo) userObject;
            //做空值判断，确保userId不为空并且该帐号的可用状态为1，并且用户类型为店家
            if (user != null && user.getUserId() != null && user.getUserId() > 0
                    && user.getEnableStatus() == 1 && user.getUserType() == 2) {
                return true;
            }
        }
        //若不满足登录验证，则直接跳转到帐号登录页面
        PrintWriter writer = response.getWriter();
        writer.print("<html><script>window.location.href = '/o2o/local/login?usertype=2'</script></html>");
        return false;
    }
}
