package com.runcoding.handler.type.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnType {

	ColumnStyle style() default ColumnStyle.JSON;

}