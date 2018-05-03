package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.MemberSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberSessionRepository extends JpaRepository<MemberSession,String> {

    MemberSession findByToken(String token);

    @Query("select t.member.id from MemberSession t where t.token = :token")
    String findMemberId(@Param("token") String token);

}
