package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {


    Long countByAccount(String account);


    UserInfo findByAccount(String account);

    @Modifying
    @Query("update UserInfo set password = :newPassword where userId = :userId and password = :password")
    Integer updatePassword(@Param("userId") String userId,@Param("password") String password,@Param("newPassword") String newPassword);

    @Modifying
    @Query("update UserInfo set password = ?2 where account = ?1 ")
    Integer updatePassword(String account,String password);


    @Modifying
    @Query("update UserInfo set email = ?3 where userId = ?1 and password = ?2")
    int updateEmail(String userId,String password,String email);

    /**
     * 根据用户id 更新信息
     * @param userId
     * @return
     */
    @Modifying
    @Query("update UserInfo set id = :id, name = :name, bankCardNo = :bankCardNo, bankName = :bankName where userId = :userId")
    int update(@Param("userId") String userId, @Param("id") String id, @Param("name") String name,
                   @Param("bankCardNo") String bankCardNo,@Param("bankName") String bankName);

    @Modifying
    @Query("update UserInfo  set balance = balance + :money where userId = :userId")
    int updateBalance(@Param("userId") String userId,@Param("money") Long money);

    @Modifying
    @Query("update UserInfo  set outerDiscAccount = :outerDiscAccount where userId = :userId")
    int updateOuterDiscAccount(@Param("userId") String userId,@Param("outerDiscAccount") String outerDiscAccount);

}
