package com.gushushu.yanao.usersys.factory;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionStatusFactoryTest {
    @Test
    public void create() throws Exception {


        String status = TransactionStatusFactory.create("INLINE_RECHARGE_TYPE");
        System.out.println(status);


    }

}