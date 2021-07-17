package com.runcoding.handler.type;

import com.runcoding.handler.type.annotation.ColumnType;
import com.runcoding.handler.type.annotation.ColumnTypeScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author runcoding
 * @date 2019-03-04
 * @desc:
 */
public class TypeHandlerScannerRegistrar extends MapperScannerRegistrar {

    private Logger  logger  = LoggerFactory.getLogger(TypeHandlerScannerRegistrar.class);

    private ResourceLoader resourceLoader;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(ColumnTypeScan.class.getName()));
        ColumnClassPathScanner scanner = new ColumnClassPathScanner(registry);
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        List<String> basePackages = new ArrayList<>();
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        scanner.registerFilters();
        /**扫描包并获取Mapper interface*/
        basePackages.forEach(basePackage->{
            Set<BeanDefinition> beanDefinitions = scanner.findCandidate(basePackage);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                String beanClassName = beanDefinition.getBeanClassName();
                try {
                    /**Mapper interface */
                    Class<?> regClass  = Class.forName(beanClassName);
                    Field[] fields = regClass.getDeclaredFields();
                    if(fields == null){
                        return;
                    }
                    for (Field field : fields) {
                        field.setAccessible(true);
                        ColumnType column = field.getAnnotation(ColumnType.class);
                        TypeHandlerRegistrar.registrarClass(column,field.getType());
                    }
                } catch (Exception e) {
                    logger.error("Mapper interface error",e);
                }
            }

        });

    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


}
