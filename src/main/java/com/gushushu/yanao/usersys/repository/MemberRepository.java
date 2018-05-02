package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,String> {


    Long countByAccount(String account);


    Member findByAccount(String account);

/*
    @Modifying
    @Query("update Member set password = :newPassword where memberId = :memberId and password = :password")
    Integer updatePassword(@Param("memberId") String memberId,@Param("password") String password,@Param("newPassword") String newPassword);
*/

/*    *//**
     * 根据用户id 更新信息
     * @param memberId
     * @return
     *//*
    @Modifying
    @Query("update UserInfo set id = :id, name = :name, bankCardNo = :bankCardNo, bankName = :bankName where memberId = :memberId")
    int update(@Param("memberId") String memberId, @Param("id") String id, @Param("name") String name,
                   @Param("bankCardNo") String bankCardNo,@Param("bankName") String bankName);*/

/*
    @Modifying
    @Query("update Member  set balance = balance + :money where memberId = :memberId")
    int updateBalance(@Param("memberId") String memberId,@Param("money") Long money);
*/


}
