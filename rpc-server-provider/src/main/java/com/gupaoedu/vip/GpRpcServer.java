package com.gupaoedu.vip;

import com.gupaoedu.vip.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 发布服务通过Spring来进行管理
 */
@Component
public class GpRpcServer implements ApplicationContextAware, InitializingBean {


	// 线程池
	ExecutorService executorService = Executors.newCachedThreadPool();

	private int port;

	private Map<String, Object> handlerMap = new HashMap<>();

	public GpRpcServer(int port) {
		this.port = port;
	}

	/**
	 * 发布服务
	 *
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

		ServerSocket serverSocket = null;
		try {

			serverSocket = new ServerSocket(port);
			// 监听客户端请求
			while (true) {
				Socket socket = serverSocket.accept();
				// 每一个socket交给processorHandler线程处理
				executorService.execute(new ProcessorHandler(socket, handlerMap));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {


		Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
		if (!serviceBeanMap.isEmpty()) {
			for (Object serviceBean : serviceBeanMap.values()) {

				// 拿到注解
				RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
				// 拿到接口名称
				String serviceName = rpcService.value().getName();

				// 拿到版本号
				String version = rpcService.version();

				if (!StringUtils.isEmpty(version)) {
					serviceName += "-" + version;
				}
				handlerMap.put(serviceName, serviceBean);
			}
		}
	}
}