package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.model.BackMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,String> ,JpaSpecificationExecutor<Member> {


    Long countByAccount(String account);

    Member findByMemberId(String memberId);

    Member findByAccount(String account);

    @Query("select t.memberId from Member t where t.password = :password and t.account = :account")
    String findId(@Param("account") String account,@Param("password") String password);

    //@Query("select t from  Member t ")
    //Page findAll(Specification<Member> specification, Pageable pageable);

    //@Query("select new com.yanyangpapa.livevideo.model.FrontMember(t.memberId,t.nickname,t.role) from  Member t ")

    @Query("select new com.gushushu.yanao.usersys.model.BackMember(t.account, t.name, t.idCard, t.idCardFrontUrl, t.idCardBehindUrl, t.bankCard, t.phoneNumber, t.createDate, t.realNameTime) from Member t")
    Page findAll(Specification<Member> specification, Pageable pageable);


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
