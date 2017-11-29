package com.gushushu.yanao.usersys.filter;

import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.model.ManagerToken;
import com.gushushu.yanao.usersys.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.servlet.*;
import java.io.IOException;
@Component
public class TokenFilter implements Filter,AppConstant {


    @Autowired
    private ObjectCache objectCache;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = servletRequest.getParameter(TOKEN);
        String userId = servletRequest.getParameter(USER_ID);
        String managerId = servletRequest.getParameter(MANAGER_ID);

        //无权限操作
        if(StringUtils.isEmpty(token)){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else if(!StringUtils.isEmpty(managerId)){//管理员操作

            ManagerToken managerToken = (ManagerToken) objectCache.get(new ManagerToken(token));
            if(managerToken != null || managerId.equals(managerToken.getManagerId())){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                servletRequest.getRequestDispatcher("/security?message=invalid token").forward(servletRequest,servletResponse);
            }


        }else if(!StringUtils.isEmpty(userId)){//用户操作

            UserToken userToken = (UserToken) objectCache.get(new UserToken(token));
           if(userToken != null || userId.equals(userToken.getUserId())){
               filterChain.doFilter(servletRequest,servletResponse);
           }else{
               servletRequest.getRequestDispatcher("/security?message=invalid token").forward(servletRequest,servletResponse);
           }

       }

    }

    public void destroy() {

    }
}
