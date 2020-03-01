package com.mooc.ydq.web.handler;

import com.mooc.ydq.beans.BeanFactory;
//import sun.misc.Request;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//功能：请求映射器
public class MappingHandler {

    //它对应的请求uri
    private String uri;

    //它对应的controller方法
    private Method method;


    //controller的类
    private Class<?> controller;

    private String[] args;

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        //将serlvetRequest转化为HttpServletRequest
        String requesturi = ((HttpServletRequest) req).getRequestURI();
        //判断uri和handler中的uri是否相等
        if(!uri.equals(requesturi)){
            return false;
        }
        //如果相等，则调用method的方法
        Object[] parameters = new Object[args.length];

        //通过参数名依次从servletRequest中获取这些参数
        for (int i = 0; i < args.length; i++){
            parameters[i] = req.getParameter(args[i]);
        }

        Object ctl = BeanFactory.getBean(controller);
        //因为controller可能会是多种类型，所以用Object类来存储结果
        //Object ctl = controller.newInstance();
        Object response = method.invoke(ctl, parameters);

        //将方法返回的结果放到servletResponse中去
        res.getWriter().println(response.toString());

        return true;
    }

    MappingHandler(String uri, Method method, Class<?> cls, String[] args){
        this.uri = uri;
        this.method = method;
        this.controller = cls;
        this.args = args;
    }


}
