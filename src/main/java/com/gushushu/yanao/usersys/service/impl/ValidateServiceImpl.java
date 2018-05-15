package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.service.ValidateService;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ValidateServiceImpl implements ValidateService {

    Logger logger = Logger.getLogger(this.getClass());
    @Override
    public ResponseEntity<ResponseBody<String>> bankCard(String bankCard){


        System.out.println("bankCard = [" + bankCard + "]");
        ResponseEntity<ResponseBody<String>> response = null;

        String url = String.format("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=%s&cardBinCheck=true&ctoken=5Fq43D63hW-Ns1tX&_=1526113873028",
                bankCard);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        try {
            httpGet.setURI(new URI(url));
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String entity = EntityUtils.toString(httpResponse.getEntity());

            System.out.println("entity = " + entity);
            int index = entity.indexOf("\"validated\":false");
            if(index > 0){
                response = ResponseEntityBuilder.<String>failed("银行卡格式不正确");
            }else{
                response = ResponseEntityBuilder.<String>success(null);
            }

        } catch (URISyntaxException e) {
            response = ResponseEntityBuilder.<String>failed(e.getMessage());
        } catch (ClientProtocolException e) {
            response = ResponseEntityBuilder.<String>failed(e.getMessage());
        } catch (IOException e) {
            response = ResponseEntityBuilder.<String>failed(e.getMessage());
        }

        System.out.println("response = " + response);

        return response;
    }
}
