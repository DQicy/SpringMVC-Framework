package com.mooc.ydq.web.mvc;

import java.lang.annotation.*;

@Documented
//需要保留到运行期
@Retention(RetentionPolicy.RUNTIME)
//说明此注解是类的注解
@Target(ElementType.TYPE)
public @interface Controller {
}
