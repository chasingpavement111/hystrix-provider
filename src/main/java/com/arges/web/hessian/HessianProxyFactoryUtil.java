package com.arges.web.hessian;

import com.arges.web.service.KindyHessianService;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 客户端连接hessian服务的工具类
 *
 * @author zhangjie
 */
public class HessianProxyFactoryUtil {

    private static <T> T getHessianClientBean(Class<T> clazz, String url) throws Exception {
        // 客户端连接工厂,这里只是做了最简单的实例化，还可以设置超时时间，密码等安全参数
        HessianProxyFactory factory = new HessianProxyFactory();

        return (T) factory.create(clazz, url);
    }

    //
    public static void main(String[] args) {

        // 服务器暴露出的地址
        String url = "http://localhost:8091/kindy";

        // 客户端接口，需与服务端对象一样
        try {
            KindyHessianService helloHessian = HessianProxyFactoryUtil.getHessianClientBean(KindyHessianService.class, url);
//            boolean msg = helloHessian.pushTransmission(1, 1, Arrays.asList("1"), "sub1", "cot1");
            boolean msg = helloHessian.pushTransmission(1, 1, null, "sub1", "cot1");

            System.out.println(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
