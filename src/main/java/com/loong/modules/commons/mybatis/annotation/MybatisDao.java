package com.loong.modules.commons.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 用于扫描Dao层接口，并交给mybatis与spring整合的
 *org.mybatis.spring.mapper.MapperScannerConfigurer类管理
 * 使dao层能接口在整合后，在spring容器中加入响应的bean对象
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface MybatisDao {
    String value() default "";
}
