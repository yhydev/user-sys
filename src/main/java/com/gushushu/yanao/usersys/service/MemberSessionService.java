package com.gushushu.yanao.usersys.service;


import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import org.springframework.http.ResponseEntity;

public interface MemberSessionService {

    /**
     * 根据token 查找member
     * @param token
     * @return
     */
    ResponseEntity<ResponseBody<Member>> findMember(String token);

    /**
     * 根据 token 查找member_id
     * @param token
     * @return
     */
    ResponseEntity<ResponseBody<String>> findMemberId(String token);


    ResponseEntity<ResponseBody<FrontMemberSession>> findSession(String token);


    /**
     * 保存会话
     * @param member
     * @return
     */
    ResponseEntity<ResponseBody<FrontMemberSession>> saveSession(String member);


}
