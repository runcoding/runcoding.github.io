package com.runcoding.handler.type;

import com.google.common.collect.Sets;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * @author runcoding
 * @date 2019-03-05
 * @desc:
 */
public class ColumnClassPathScanner extends ClassPathMapperScanner {

    String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    public ColumnClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public Set<BeanDefinition> findCandidate(String basePackage){
        String packageSearchPath = "classpath*:" + this.resolveBasePackage(basePackage) + '/' + this.DEFAULT_RESOURCE_PATTERN;
        ResourcePatternResolver resourceLoader = (ResourcePatternResolver) getResourceLoader();
        Set<BeanDefinition> beanDefinitions = Sets.newHashSet();
        try {
            Resource[] resources =  resourceLoader.getResources(packageSearchPath);
            if(resources == null){
                return beanDefinitions;
            }
            for (Resource resource:resources) {
                if (!resource.isReadable()) {
                   continue;
                }
                MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                if (this.isCandidateComponent(metadataReader)) {
                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                    sbd.setResource(resource);
                    sbd.setSource(resource);
                    beanDefinitions.add(sbd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanDefinitions;
    }

}
