package com.arges.web.service;

import com.arges.web.service.impl.FooAOPServiceImpl;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public interface FooAOPService {
    default void defaultMethod() {
        System.out.println("this is from default method defined from interface");
    }

    public static class Tester {
        public static void main(String[] args){
            FooAOPService service = new FooAOPServiceImpl();
            service.defaultMethod();
        }
    }
}
