package com.gushushu.yanao.usersys.filter;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.gushushu.yanao.usersys.controller.ImageCodeController;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class ImageCodeAuthFilter implements Filter {


    @Autowired
    private DefaultKaptcha defaultKaptcha;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String imageCode = servletRequest.getParameter("imageCode");

        if (!StringUtils.isEmpty(imageCode)){

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpSession session = request.getSession();
            Object realImageCode = session.getAttribute(ImageCodeController.IMAGE_CODE);
            if(imageCode.equals(realImageCode)){
                session.setAttribute(ImageCodeController.IMAGE_CODE,Math.random());
            }else{
                request.getRequestDispatcher("/warning?message=图片验证码错误").forward(request,servletResponse);
                return;

            }
        }

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
