package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.annotation.HandlerRole;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static com.gushushu.yanao.usersys.service.impl.MemberServiceImpl.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;



    @GetMapping("/findOne")
    @HandlerRole({MemberType.MANAGER_TYPE})
    public ResponseEntity findOne(String memberId){
        return memberService.findOne(memberId);
    }



    @HandlerRole({MemberType.MANAGER_TYPE})
    @GetMapping("/findList")
    public ResponseEntity getList(MemberService.SearchParam<BackMember> searchParam){
        searchParam.setResultBean(QBeans.MEMBER_MANAGER);
        return memberService.search(searchParam);
    }




    @HandlerRole(MemberType.MANAGER_TYPE)
    @PostMapping(params = {"type"})
    public ResponseEntity create(MemberService.CreateParam createParam){
        return memberService.create(createParam);
    }


    @PostMapping
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
        
        createParam.setType(MemberType.USER_TYPE);
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



    @RequestMapping("/rejectOpenAccount")
    @HandlerRole({MemberType.MANAGER_TYPE})
    public ResponseEntity updateMember(String memberId){
        MemberService.UpdateOneParam updateOneParam = new  MemberService.UpdateOneParam(memberId);
        //用户必须是没有开户
        updateOneParam.setOpenAccountStatus(MemberOpenAccountStatus.REJECT);
        updateOneParam.eqOpenAccount(MemberServiceImpl.MemberOpenAccountStatus.APPLY_FOR);

        return memberService.update(updateOneParam);
    }


    @HandlerRole({MemberType.MANAGER_TYPE})
    @RequestMapping("/setInnerDiscAccount")
    public ResponseEntity setInnerDiscAccount(@Validated MemberService.SetInnerDiscAccountParam setInnerDiscAccountParam){
        return memberService.setInnerDiscAccount(setInnerDiscAccountParam);
    }

}
