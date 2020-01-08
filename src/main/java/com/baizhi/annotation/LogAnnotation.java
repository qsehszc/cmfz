package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 表示该注解何时生效
@Target(ElementType.METHOD) // 表示该注解可以在何处加入
public @interface LogAnnotation {
    String value();
}
