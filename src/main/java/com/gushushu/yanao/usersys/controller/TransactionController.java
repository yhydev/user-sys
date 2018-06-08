package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.annotation.HandlerRole;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import com.gushushu.yanao.usersys.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static com.gushushu.yanao.usersys.service.impl.MemberServiceImpl.*;
import static com.gushushu.yanao.usersys.service.MemberSessionService.*;
import static com.gushushu.yanao.usersys.common.QBeans.*;


@RestController
@RequestMapping("/transaction")
public class TransactionController {


    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private TransactionService transactionService;


    @RequestMapping("/offlineDeposit")
    public ResponseEntity offlinePay(@Validated TransactionService.OfflineDepositParam offlineDepositParam){
        return transactionService.offlineDeposit(offlineDepositParam);
    }

    @RequestMapping("/offlineWithdraw")
    public ResponseEntity offlinePay(@Validated TransactionService.OfflineWithdrawParam offlineWithdrawParam){
        return transactionService.offlineWithdraw(offlineWithdrawParam);
    }

    @HandlerRole({MemberType.USER_TYPE,MemberType.MANAGER_TYPE})
    @GetMapping(params = {"transactionId"})
    public ResponseEntity transaction(String transactionId){
        return transactionService.detail(transactionId);
    }

    /**
     * 查询交易列表
     * @param searchParam
     * @param token
     * @return
     */
    @GetMapping("/findList")
    @HandlerRole({MemberType.USER_TYPE,MemberType.MANAGER_TYPE})
    public ResponseEntity transactionList(TransactionService.SearchParam searchParam,@CookieValue(value = "token")String token){

         ResponseEntity response = null;

         FindOneParam<MemberTypeModel> findOneParam = new FindOneParam(MEMBER_TYPE);
         findOneParam.setEqToken(token);
         ResponseBody<MemberTypeModel> memberTypeResponseBody = memberSessionService.findOne(findOneParam).getBody();

         if(memberTypeResponseBody.isSuccess()){
             MemberTypeModel memberTypeModel = memberTypeResponseBody.getData();
             if(memberTypeModel.getType().equals(MemberType.USER_TYPE)){
                 //用户查询交易
                 searchParam.setMemberId(memberTypeModel.getMemberId());
                 response = transactionService.search(searchParam,TransactionServiceImpl.FRONT_TRANSACTION_Q_BEAN);
             }else if(memberTypeModel.getType().equals(MemberType.MANAGER_TYPE)){
                 //管理员查询交易
                 response = transactionService.search(searchParam,TransactionServiceImpl.BACK_TRANSACTION_Q_BEAN);
             }
         }

        return response;
    }



    /**
     * 更新交易
     * @return
     */
    @PutMapping//TODO 管理员权限
    @HandlerRole({MemberType.MANAGER_TYPE})
    public ResponseEntity update(TransactionService.UpdateParam updateParam){
        return transactionService.update(updateParam);
    }


}
