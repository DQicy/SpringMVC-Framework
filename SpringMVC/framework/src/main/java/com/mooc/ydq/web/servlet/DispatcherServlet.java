package com.mooc.ydq.web.servlet;

import com.mooc.ydq.web.handler.HandlerManager;
import com.mooc.ydq.web.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

//需要实现五个预定义的方法-->本项目只需要实现service方法即可
public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
       //在相应内写入test即可
        //res.getWriter().println("test");

        for (MappingHandler mappingHandler : HandlerManager.mappingHandlerList){
            try {
                if (mappingHandler.handle(req, res)){
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
