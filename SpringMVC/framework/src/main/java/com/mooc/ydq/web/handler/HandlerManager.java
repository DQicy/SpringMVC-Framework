package com.mooc.ydq.web.handler;

import com.mooc.ydq.web.mvc.Controller;
import com.mooc.ydq.web.mvc.RequestMapping;
import com.mooc.ydq.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandlerManager {

    //设置一个静态属性，用来保存多个mappingHandler
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    //把controller类挑选出来，将类中的mappingHandler初始化为mappinghandler
    public static void resolveMappingHandler(List<Class<?>> classList){
        //首先遍历类，将带有@controller注解的挑选出来
        for (Class<?> cls : classList){
            //如果这个类上有@Controller注解
            if(cls.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(cls);
            }

        }
    }

    private static void parseHandlerFromController(Class<?> cls){
        //通过反射的方式获取类中的所有方法
        Method[] methods = cls.getDeclaredMethods();
        //然后找到被@RequestMapping的方法
        for (Method method : methods){
            if(!method.isAnnotationPresent(RequestMapping.class)){
                continue;
            }
            //从方法的属性中获取所有能构成MappingHandler的属性
            //uri
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();

            //设置一个容器来存储参数
            List<String> paramNameList = new ArrayList<>();
            for (Parameter parameter : method.getParameters()){
                //找到被@RequestParam注解的参数
                if (parameter.isAnnotationPresent(RequestParam.class)){
                        paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);

            //然后构造一个mappingHandler,将参数传入进去
            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);

            //最后把构造好的mappingHandler放到handler管理器的静态属性中
            HandlerManager.mappingHandlerList.add(mappingHandler);

        }
    }

}
