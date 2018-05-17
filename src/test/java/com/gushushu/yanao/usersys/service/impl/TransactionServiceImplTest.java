package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.model.FrontTransaction;
import com.gushushu.yanao.usersys.repository.OfflinePayRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceImplTest implements AppConstant{

    @Autowired
    private TransactionService transactionService;

    @Autowired
    MemberSessionService memberSessionService;

    @Autowired
    MemberService memberService;



    String account = "13000000001";
    String password = "888888";
    String accountId = null;
    String token = null;


    @Before
    public void initialize(){

        MemberService.LoginParam loginParam = new MemberService.LoginParam();

        loginParam.setAccount(account);
        loginParam.setPassword(password);

        ResponseEntity<ResponseBody<FrontMemberSession>> memberSessionResponse = memberService.login(loginParam);

        assert  memberSessionResponse.getBody().isSuccess();

        accountId = memberSessionResponse.getBody().getData().getAccount();
        token = memberSessionResponse.getBody().getData().getToken();

    }

    @Test
    public void searchFront(){

        /*TransactionService.SearchFrontParam searchFrontParam = new TransactionService.SearchFrontParam();

        searchFrontParam.setToken(token);

        transactionService.searchFront(searchFrontParam);*/


        TransactionService.SearchParam searchParam = new TransactionService.SearchParam();
        searchParam.setType(TransactionService.OFFLINE_PAY_TYPE);

        transactionService.search(searchParam,TransactionServiceImpl.frontTransactionQBean);
      //  searchParam.set

    }






    @Test
    public void underLinePay(){

        TransactionService.OfflinePayParam underLinePayParam = new TransactionService.OfflinePayParam();

        underLinePayParam.setMoney(100L);
        underLinePayParam.setToken(token);
        underLinePayParam.setReceiveAccount("6212261702013626387");
        underLinePayParam.setPayAccount("6212261702013626387");
        transactionService.offlinePay(underLinePayParam);



        underLinePayParam.setReceiveAccount("2222");
        underLinePayParam.setPayAccount("1111");
        transactionService.offlinePay(underLinePayParam);


        underLinePayParam.setReceiveAccount("6212261702013626387");
        underLinePayParam.setPayAccount("6225365271562822");
        transactionService.offlinePay(underLinePayParam);

    }



    @Test
    public void update(){


        TransactionService.OfflinePayParam underLinePayParam = new TransactionService.OfflinePayParam();

        underLinePayParam.setMoney(100L);
        underLinePayParam.setToken(token);
        underLinePayParam.setReceiveAccount("6212261702013626387");
        underLinePayParam.setPayAccount("6225365271562822");
        ResponseEntity<ResponseBody<String>> offlinePayResponse =  transactionService.offlinePay(underLinePayParam);


        assert offlinePayResponse.getBody().isSuccess();


        TransactionService.UpdateParam updateParam = new TransactionService.UpdateParam();
        updateParam.setRemark("哈哈哈");
        updateParam.setSuccess(false);
        updateParam.setTransactionId(offlinePayResponse.getBody().getData()+1);
        transactionService.update(updateParam);


        updateParam.setRemark("哈哈哈");
        updateParam.setSuccess(false);
        updateParam.setTransactionId(offlinePayResponse.getBody().getData());
        transactionService.update(updateParam);

        transactionService.update(updateParam);



    }


    @Test
    public void offlineWithdraw(){

        TransactionService.OfflineWithdrawParam offlineWithdrawParam = new TransactionService.OfflineWithdrawParam();
        offlineWithdrawParam.setMoney(1000L);
        offlineWithdrawParam.setToken(token);

        transactionService.offlineWithdraw(offlineWithdrawParam);


    }


}
