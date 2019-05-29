package com.arges.web.service.impl;

import com.arges.web.service.FooAOPService;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public class FooAOPServiceImpl implements FooAOPService {
    @Override
    public void defaultMethod() {
        System.out.println("this is from ** default ** method defined from  ** class **");
    }

    public final void finalMethod(){
        System.out.println("this is from ** final ** method defined from ** class **");
    }
}
