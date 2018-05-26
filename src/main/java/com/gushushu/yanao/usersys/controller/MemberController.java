package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;

    @RequestMapping("/register")
    public ResponseEntity register(@Validated MemberService.CreateParam createParam,
                                   @NotBlank(message = "验证码不能为空") String phoneCode){

        //验证码 验证
        IdentifyingCodeService.ValidateParam validateParam = new IdentifyingCodeService.ValidateParam();
        validateParam.type = "register";
        validateParam.phone = createParam.getAccount();
        validateParam.code = phoneCode;

        ResponseEntity<ResponseBody<IdentifyingCode>> responseEntity = identifyingCodeService.validate(validateParam);

        if(!responseEntity.getBody().isSuccess()){
            return responseEntity;
        }

        return memberService.create(createParam);
    }

    @RequestMapping("/login")
    public ResponseEntity login(@Validated MemberService.LoginParam loginParam){
        return memberService.login(loginParam);
    }

    @RequestMapping("/openAccount")
    public ResponseEntity openAccount(@Validated MemberService.ApplyForAccountParam realNameParam){
        return memberService.applyForAccount(realNameParam);
    }




    @RequestMapping("/getFrontMember")
    public ResponseEntity getFrontMember(String token){
        return  memberService.getFrontMember(token);
    }



}
