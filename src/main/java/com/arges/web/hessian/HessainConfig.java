package com.arges.web.hessian;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.arges.web.service.KindyHessianService;
import com.arges.web.service.KitchenHessianService;

/**
 * 暴露Service服务访问地址
 * <p>
 * 客户端通过url=“本服务的访问根地址/kitchen”，获取 service实例，然后可直接调用service的方法
 * {
 * HessianProxyFactory factory = new HessianProxyFactory();
 * factory.setOverloadEnabled(true);
 * T service = (T) factory.create(interfaceClass, url);
 * return service;
 * }
 *
 * @author zhangjie
 */
@Configuration
@AllArgsConstructor
public class HessainConfig {

    private final KitchenHessianService kitchenService;

    private final KindyHessianService kindyService;

    @Bean("/kitchen")
    public HessianServiceExporter kitchenService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(kitchenService);
        exporter.setServiceInterface(KitchenHessianService.class);
        return exporter;
    }

    @Bean("/kindy")
    public HessianServiceExporter kindyService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(kindyService);
        exporter.setServiceInterface(KindyHessianService.class);
        return exporter;
    }
}
