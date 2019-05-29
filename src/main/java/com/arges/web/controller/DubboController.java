package com.arges.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yowei.web.entity.UserInfo;
import com.yowei.web.service.UserInfoService;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    public UserInfo get(){
        return userInfoService.getOneById(1L);
    }
}
