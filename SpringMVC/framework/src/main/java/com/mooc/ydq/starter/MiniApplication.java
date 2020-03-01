package com.mooc.ydq.starter;

import com.mooc.ydq.beans.BeanFactory;
import com.mooc.ydq.core.ClassScanner;
import com.mooc.ydq.web.handler.HandlerManager;
import com.mooc.ydq.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class MiniApplication {
    public  static  void  run(Class<?> cls, String[] args){
        System.out.println("Hello min-spring!");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();

            List<Class<?>>  classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);

            HandlerManager.resolveMappingHandler(classList);

            classList.forEach(it-> System.out.println(it.getName()));
        } catch (LifecycleException e) {
            //打印异常栈
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
