package com.gushushu.yanao.usersys.factory;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class TransactionStatusFactory  {


    private static  Map statusMap = null;

    public static String create(String type){
        if(statusMap == null){
            InputStream is = TransactionStatusFactory.class.getClass().getResourceAsStream("/transactionStatus.properties");
            Properties properties = new Properties();
            try {
                properties.load(is);
                statusMap = properties;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return (String) statusMap.get(type);
    }



}
