package com.mooc.ydq.web.mvc;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
//此注解用于接收参数
@Target(ElementType.PARAMETER)
public @interface RequestParam {

    String value();
}
