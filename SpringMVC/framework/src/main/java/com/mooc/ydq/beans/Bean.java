package com.mooc.ydq.beans;

import java.lang.annotation.*;

//功能：用于解析一个类，将其添加为bean
@Documented
@Retention(RetentionPolicy.RUNTIME)
//注解到类上
@Target(ElementType.TYPE)
public @interface Bean {

}
