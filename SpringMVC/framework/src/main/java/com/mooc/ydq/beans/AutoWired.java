package com.mooc.ydq.beans;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
//注解在类属性上
@Target(ElementType.FIELD)
public @interface AutoWired {

}
