package com.runcoding.configurer;

import com.runcoding.service.support.elastic.BusinessElasticsearchTemplate;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * @author runcoding
 */
@Configuration
public class ElasticSearchConfig extends ElasticsearchDataAutoConfiguration{

    @Value("${spring.data.elasticsearch.indexName:product}")
    private String indexName;

    @Bean
    public String indexName(){
        return indexName;
    }

    @Bean
    @Primary
    @Override
    public ElasticsearchTemplate elasticsearchTemplate(Client client,
                                                       ElasticsearchConverter converter) {
        try {
            return new BusinessElasticsearchTemplate(client, converter);
        }catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}


