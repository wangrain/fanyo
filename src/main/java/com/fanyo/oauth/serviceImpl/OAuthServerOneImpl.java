package com.fanyo.oauth.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.fanyo.oauth.bean.OAuthBean;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fanyo.common.Constants;
import com.fanyo.exception.AppException;
import com.fanyo.oauth.service.OAuthService;
import com.fanyo.util.DateTimeUtils;
import com.fanyo.util.HttpClient431Util;
import com.fanyo.util.MD5Util;

/**
 * 名称：  OAuthServerOneImpl
 * 作者:   rain.wang
 * 日期:   2016/9/9
 * 简介:
 */
@Service
public class OAuthServerOneImpl implements OAuthService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public OAuthBean getReqToken() {
        Map param = new HashMap<String,String>();
        param.put("oauth_consumer_key",Constants.CONSUMER_KEY);
        param.put("oauth_signature_method",Constants.AUTH_METHOD);
        param.put("oauth_signature",null);
        param.put("oauth_timestamp", DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS));
        param.put("oauth_nonce", RandomStringUtils.randomNumeric(6));
        param.put("oauth_signature",getSign(Constants.HTTP_METHOD,Constants.REQ_TOKEN_URL,param));
        String result ="";
        try {
            result = HttpClient431Util.doGet(param, Constants.REQ_TOKEN_URL);
        }catch (Exception e){
            log.error("请求TOKEN错误",e);
        }
        String[] results = result.split("&");
        if(results.length!=2){
            log.error("请求TOKEN错误，返回："+result);
            return null;
        }
        OAuthBean oAuthBean = new OAuthBean();
        oAuthBean.setOauthToken(results[0]);
        oAuthBean.setOauthToken(results[1]);
        return oAuthBean;
        
    }

    private String getSign(String httpMethod, String url,Map param){
        StringBuffer sign = null;
        String result = null;
        try {
            //请求网址Encode
            url = URLEncoder.encode(url, Constants.CHARSET);
            //请求参数排序
            Set<String> keySet = param.keySet();
            List<String> sortedList = new ArrayList(keySet);
            Collections.sort(sortedList);
            //开始拼接sign
            sign = new StringBuffer(httpMethod);
            sign.append('&');
            sign.append(url);
            sign.append('&');
            for(String key : sortedList){
                String value = (String) (param.get(key)==null?"":param.get(key));
                sign.append(key+"="+value+"&");
            }
            log.info("签名字符串：{}",sign);
            result = MD5Util.getMessageDigest(sign.toString(),Constants.AUTH_METHOD);
        }catch (UnsupportedEncodingException e) {
            log.error("转换URL出错：",e);
            throw new AppException("E999","转换URL出错");
        }catch (Exception e){
            log.error("生成签名sign异常：",e);
            throw new AppException("E999","生成签名sign异常");
        }

        return result;
    }

}
