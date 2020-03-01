package com.mooc.ydq.beans;

import com.mooc.ydq.web.mvc.Controller;
import javafx.beans.binding.ObjectExpression;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    //定义一个属性 bean类型到bean实例的映射
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls){
        return  classToBean.get(cls);
    }

    //bean的初始化，传入的是类定义
    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        //当容器内有类定义时，就要初始化bean
        while (toCreate.size() != 0){
            //定义一个变量来保存当前容器的大小
            int remainSize = toCreate.size();
            for(int i = 0; i < toCreate.size(); i++){
                if(finishCreate(toCreate.get(i))){
                    toCreate.remove(i);
                }
            }
            //防止陷入循环依赖
            if(toCreate.size() == remainSize){
                throw new Exception("cycle dependency!");
            }
        }

    }

    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        //如果这个类被@Bean或者@Controller注解的话，bean工厂就会创建
        if(!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)){
            return true;
        }
        Object bean = cls.newInstance();
        //接下来需要注入依赖
        //如果这个bean的属性中有@AutroWired注解，则需要注入
        for (Field field : cls.getDeclaredFields()){
            if (field.isAnnotationPresent(AutoWired.class)){
                //从bean工厂内获取相对应的属性bean
                Class<?> fieldType = field.getType();
                //从bean工厂中找到相应的依赖bean
                Object reliantBean = BeanFactory.getBean(fieldType);
                if(reliantBean == null){
                    return false;
                }
                //先设置一下这个字段的可及属性
                field.setAccessible(true);
                field.set(bean, reliantBean);
            }
        }
        classToBean.put(cls, bean);
        return true;
    }






}
