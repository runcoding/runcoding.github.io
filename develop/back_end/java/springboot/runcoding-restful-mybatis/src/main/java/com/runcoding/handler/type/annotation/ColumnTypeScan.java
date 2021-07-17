package com.runcoding.handler.type.annotation;

import com.runcoding.handler.type.TypeHandlerScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(TypeHandlerScannerRegistrar.class)
/***
 * basePackages包下扫描类，
 * 支持全局对象映射json，但是会影响正常的json对象操作数据dao
 */
public @interface ColumnTypeScan {

    String[] basePackages() default {};

}
