package com.twotwo.interceptor;

import com.twotwo.common.UserContext;
import com.twotwo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		Object user = request.getSession().getAttribute("user");
//		if (user == null) {
//			R<String> result = R.error("用户未登录");
//			ServletUtil.write(response, JSONUtil.toJsonStr(result), "application/json; charset=utf-8");
//			return false;
//		}
        User user =new User();

        user.setId(10);
        user.setUsername("兔兔111");
        user.setPassword("565173594");
        user.setRole(3);
        user.setCompanyId(1);
        user.setDepartmentId(3);
        user.setStatus(1);
        UserContext.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}