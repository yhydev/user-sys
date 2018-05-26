package com.gushushu.yanao.usersys.command;


import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initializeManager implements CommandLineRunner {

    public static final String ACCOUNT = "13000000000";
    public static final String PASSWORD = "";

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public void run(String... args) throws Exception {
        memberRepository.findAccount("");
    }
}
