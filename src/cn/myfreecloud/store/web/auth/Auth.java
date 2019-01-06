package cn.myfreecloud.store.web.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类
 */

//表明用在方法上
@Target(ElementType.METHOD)
//保留在运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    String value();
}
