package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.ManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerInfoRepository extends JpaRepository<ManagerInfo,String> {
    @Modifying
    @Query("update ManagerInfo set password = :newPassword where managerId = :managerId and password = :password")
    int updatePassword(@Param("managerId") String managerId,@Param("password") String password,@Param("newPassword")String newPassword);

    int countByAccount(String account);

    ManagerInfo findByAccount(String account);

}
