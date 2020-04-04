package com.gupaoedu.vip;

import com.gupaoedu.vip.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//       IHelloService helloService=new HelloServiceImpl();
//
//       RpcProxyServer proxyServer=new RpcProxyServer();
//       proxyServer.publisher(helloService,8080);

        // 通过加载Spring的配置类进行启动
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext) context).start();


    }
}
