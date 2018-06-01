package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MemberSessionRepository extends JpaRepository<MemberSession,String> {

    MemberSession findByToken(String token);

    @Query("select t.member.id from MemberSession t where t.token = :token")
    String findMemberId(@Param("token") String token);

    @Query("select t.member.type from MemberSession t where t.token = :token")
    String findMemberTypeByToken(@Param("token") String token);

    @Query("select new com.gushushu.yanao.usersys.model.FrontMemberSession(t.member.memberId, t.token, t.member.account) from MemberSession t where t.token = :token")
    FrontMemberSession findSession(@Param("token") String token);

}
