package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.model.FrontMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,String> {


    Long countByAccount(String account);

    Long countByMemberId(String id);

    Long countByType(String type);

    Long countByIdCardAndStatus(String idCard,String openAccountStatus);



    @Query("select t.memberId from Member t where t.account = :account and t.password = :password")
    String findMemberId(@Param("account") String account,@Param("password")String password);



    Member findByMemberId(String memberId);

    Member findByAccount(String account);



    @Query("select new com.gushushu.yanao.usersys.model.FrontMember(t.openAccountStatus, t.innerDiscAccount,t.account,t.type) from Member t where t.memberId = :id")
    FrontMember findFront(@Param("id") String id);

    @Query("select t.account from Member t where  t.memberId = :memberId")
    String findAccount(@Param("memberId") String memberId);

    @Query("select t.memberId from Member t where t.password = :password and t.account = :account")
    String findId(@Param("account") String account,@Param("password") String password);

}
