package com.mooc.ydq.web.mvc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
//说明此注解是方法的注解
@Target(ElementType.METHOD)
public @interface RequestMapping {

    //添加一个属性，保存需要映射的uri
    String value();

}
