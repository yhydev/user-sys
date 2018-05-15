package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/identifyingCode")
public class IdentifyingCodeController {

    @Autowired
    public IdentifyingCodeService identifyingCodeService;

    @RequestMapping(value = "/send")
    public ResponseEntity send(@Validated IdentifyingCodeService.SendParam sendParam,@NotEmpty String imageCode){
        return identifyingCodeService.send(sendParam);
    }


}
