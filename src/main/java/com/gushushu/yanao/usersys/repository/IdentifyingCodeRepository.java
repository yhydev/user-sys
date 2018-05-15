package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface IdentifyingCodeRepository extends JpaRepository<IdentifyingCode,String> {

    @Query("select max(t.createDate) from IdentifyingCode t where t.phone = :phone")
    Date findLastTime(@Param("phone") String phone);

    IdentifyingCode findByIdentifyingCodeId(String id);


    @Query("select t from IdentifyingCode t where t.phone = :phone and t.type = :type and t.status = :status and t.code = :code")
    IdentifyingCode findCode(@Param("phone") String phone, @Param("type") String type, @Param("status") String status,@Param("code") String code);

}
