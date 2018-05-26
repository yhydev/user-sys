/*
package com.gushushu.yanao.usersys.filter;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {



    private String tokenType;
    private String tokenParamName;

    @Autowired
    private MemberSessionService memberSessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        tokenType = filterConfig.getInitParameter("tokenType");
        tokenParamName = filterConfig.getInitParameter("tokenParamName");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = servletRequest.getParameter(tokenParamName);

        if(!StringUtils.isEmpty(token)){
            ResponseEntity<ResponseBody<Member>> findMemberResp = memberSessionService.findMember(token);

            ResponseBody<Member> memberBody = findMemberResp.getBody();

            if(!memberBody.isSuccess() && !tokenType.equals(memberBody.getData().getType())){
                servletRequest.getRequestDispatcher(String.format("/warning?message=%s",memberBody.getMessage())).forward(servletRequest,servletResponse);
                return;
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
*/
