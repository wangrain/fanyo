package com.fanyo.oauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.fanyo.oauth.bean.OAuthBean;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanyo.oauth.service.OAuthService;

import lombok.extern.slf4j.Slf4j;

/**
 * 名称：  OAuthController
 * 作者:   rain.wang
 * 日期:   2016/9/9
 * 简介:
 */
@Slf4j
@Controller
public class OAuthController {

    @Resource
    private OAuthService oAuthService;

    /**
     * 获取ReqToken
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/getReqToken",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getReqToken(HttpServletRequest request, HttpServletResponse response){
        log.info("---向饭否服务器获取Request Token 开始---");
        OAuthBean oAuthBean = oAuthService.getReqToken();
        return JSON.toJSONString(oAuthBean);
    }
}
