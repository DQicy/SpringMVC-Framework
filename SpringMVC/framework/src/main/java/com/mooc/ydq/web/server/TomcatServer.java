package com.mooc.ydq.web.server;

import com.mooc.ydq.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

//专门用于处理tomcat server的类
public class TomcatServer {

    private Tomcat tomcat;

    //用于tomcat的配置参数
    private String[] args;

    //tomcat的constructor方法
    public TomcatServer(String[] args){
        this.args = args;
    }

    //启动tomcat的主方法
    public void startServer() throws LifecycleException {
        //实例化一个tomcat即可
        tomcat = new Tomcat();
        //设置tomcat的监听方法
        tomcat.setPort(6699);
        tomcat.start();

        //StandardContext为tomcat内对于context容器的标准实现
        Context context = new StandardContext();
        context.setPath("");

        //设置一个默认的监听器
        context.addLifecycleListener(new Tomcat.FixContextListener());

        //将test实例化注册到servlet容器中
        DispatcherServlet servlet = new DispatcherServlet();
        Tomcat.addServlet(context, "dispatcherServlet", servlet).setAsyncSupported(true);

        //配置一个servlet到uri的映射  (这样使得访问这个uri时可以映射到这个servlet)
        context.addServletMappingDecoded("/", "dispatcherServlet");

        //Tomcat的context容器需要依附在host容器内，这里将其注册到默认的host容器
        tomcat.getHost().addChild(context);

        //为了防止服务器中途退出,设置一个等待线程
        Thread awaitThread = new Thread("tomcat_await_thread"){
            @Override
            public void run(){
                //声明tomcat线程一直在等待
                TomcatServer.this.tomcat.getServer().await();
            }

        };

        //将此线程设置为非守护线程
        awaitThread.setDaemon(false);

        //调用方法，让它一直在等待
        awaitThread.start();

    }

}
