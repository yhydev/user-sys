package com.gushushu.yanao.usersys.common;

import com.gushushu.yanao.usersys.common.annotation.HandlerRole;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.repository.MemberSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class HandlerRoleInterceptor implements HandlerInterceptor {



    private Map<Object,HandlerRole> handlerRoleMapping = new HashMap<Object,HandlerRole>();
    private Map<Object,Boolean> hasRoleMapping = new HashMap<>();

    @Autowired
    private MemberSessionRepository memberSessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

      /*  if(true){
            return true;
        }*/

        boolean ret = false;

        Boolean hasValidate = hasRoleMapping.get(handler);

        if(hasValidate == null){
            addHandlerMapping(handler);
            ret = preHandle(request,response,handler);
        }else if(hasValidate){
            String token = null;
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }

            if(!StringUtils.isEmpty(token)){
                HandlerRole handlerRole = handlerRoleMapping.get(handler);
                String[] roles = handlerRole.value();

                String role = memberSessionRepository.findMemberTypeByToken(token);

                for (int i=0;i<roles.length;i++){
                    boolean hasRole = roles[i].equals(role);
                    if(hasRole){
                        return  true;
                    }
                }
            }
            try {
                request.getRequestDispatcher("/warning?message=权限不足").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            ret = true;
        }


        return ret;
    }




    private void addHandlerMapping(Object handler){

        boolean hasValidate = handler instanceof HandlerMethod;

        if(hasValidate){
            HandlerRole handlerRole = ((HandlerMethod) handler).getMethodAnnotation(HandlerRole.class);
            if(hasValidate = handlerRole != null){
                handlerRoleMapping.put(handler,handlerRole);
            }
        }

        hasRoleMapping.put(handler,hasValidate);

    }


   /* private boolean hasRoleValidator(Object handler){
        if(handler instanceof HandlerMethod){
            HandlerRole handlerRole = ((HandlerMethod) handler).getMethodAnnotation(HandlerRole.class);
            if(handler != null){
                handlerRoleMapping.put(handler,handlerRole);
            }
        }
    }*/


}
