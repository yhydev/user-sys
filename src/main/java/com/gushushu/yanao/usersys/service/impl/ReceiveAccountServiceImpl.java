package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.ReceiveAccount;
import com.gushushu.yanao.usersys.repository.ReceiveAccountRepository;
import com.gushushu.yanao.usersys.service.ReceiveAccountService;
import com.gushushu.yanao.usersys.service.ValidateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReceiveAccountServiceImpl implements ReceiveAccountService {


    final Logger logger = Logger.getLogger(ReceiveAccountServiceImpl.class);

    @Autowired
    private ReceiveAccountRepository receiveAccountRepository;

    @Autowired
    private ValidateService validateService;

    private boolean canSave(String cardNo){
        return !receiveAccountRepository.existsByBankNo(cardNo);
    }

    private boolean canUpdate(String id,String cardNo){
        return id.equals(receiveAccountRepository.findIdByBankNo(cardNo));
    }

    public ResponseEntity<ResponseBody<String>> saveOrUpdate(ReceiveAccount receiveAccount){

        logger.info("receiveAccount = [" + receiveAccount + "]");
        ResponseEntity<ResponseBody<String>> response = null;
        String errmsg = null;
        String bankNo = receiveAccount.getBankNo();
        String id = receiveAccount.getReceiveAccountId();
        ResponseEntity<ResponseBody<String>> validateBankResponse =  validateService.bankCard(bankNo);
        //银行卡是否有效
        if(validateBankResponse.getBody().isSuccess()){
            //数据是否存在，存在就保存，否则就更新
            boolean isExists = id == null? false : receiveAccountRepository.existsById(receiveAccount.getReceiveAccountId());

            if((!isExists && canSave(bankNo)) || (isExists  && canUpdate(id,bankNo))){
                receiveAccountRepository.save(receiveAccount);
                response = ResponseEntityBuilder.success(receiveAccount.getReceiveAccountId());
            }else{
                errmsg = "银行卡已存在";
            }
        }else{
            errmsg = validateBankResponse.getBody().getMessage();
        }

        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }

        logger.info("errmsg = " + errmsg);
        return response;

    }




    @Override
    public ResponseEntity<ResponseBody<List<ReceiveAccount>>> findAll() {
        return ResponseEntityBuilder.success(receiveAccountRepository.findAll());
    }


}
