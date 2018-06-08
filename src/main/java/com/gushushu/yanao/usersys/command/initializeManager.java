package com.gushushu.yanao.usersys.command;


import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * initialize manager member,if exists not create
 *@
 */
@Component
public class initializeManager implements CommandLineRunner {

    public static final String ACCOUNT = "13000000000";
    public static final String PASSWORD = "888888";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Override
    public void run(String... args) throws Exception {
        Long count = memberRepository.countByType(MemberServiceImpl.MemberType.MANAGER_TYPE);
        if(count == 0){
            MemberService.CreateParam createParam = new MemberService.CreateParam();
            createParam.setType(MemberServiceImpl.MemberType.MANAGER_TYPE);
            createParam.setAccount(ACCOUNT);
            createParam.setPassword(PASSWORD);
            memberService.create(createParam);
        }
    }
}
