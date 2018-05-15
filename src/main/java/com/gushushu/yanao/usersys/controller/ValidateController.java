package com.gushushu.yanao.usersys.controller;


import com.gushushu.yanao.usersys.service.ValidateService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    private ValidateService validateService;

    @RequestMapping("bankCard")
    public ResponseEntity bankCard(@NotEmpty @Length(min = 16,max = 19) String bankCard){
       return validateService.bankCard(bankCard);
    }


}
