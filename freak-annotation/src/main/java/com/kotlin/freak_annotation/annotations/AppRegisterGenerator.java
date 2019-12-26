package com.kotlin.freak_annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用于在类上的注解
@Target(ElementType.TYPE)
//告诉编译器在处理注解的时候在源码阶段处理，打包apk就不用
@Retention(RetentionPolicy.SOURCE)
public @interface AppRegisterGenerator {
    String packageName();

    Class<?> registerTemplete();

}

