package com.runcoding.service.support.elastic.repositorys;


import com.runcoding.model.trade.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Created by runcoding on 2017/6/9.
 * 处理的域对象是OrderPo，其主键类型是Long(id的类型)
 * @see  //es.yemengying.com/4/4.4/4.4.1.html
 * https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/
 */
@Repository
public interface TradeRepository extends ElasticsearchRepository<Trade, Long> {

    /**{"bool" : {"must" :  {"field" : {"tradeId" : "?"}}}}*/
    Trade findByTradeId(Long  tradeId);

    @Query("{\"bool\":{\"must\":{\"query_string\":{\"query\":\"?0\",\"fields\":[\"tradeId\"],\"default_operator\":\"and\"}}}}")
    Page<Trade> findByTradeId2(Long  tradeId, Pageable pageable);

    @Query("{\"bool\":{\"must\":[{\"term\":{\"tradeId\":\"?0\"}},{\"term\":{\"userId\":\"?1\"}}]}}")
    Page<Trade> findByTradeAndUserId(Long  tradeId, Long userId ,Pageable pageable);

      /**{"bool" : {"must" : [ {"field" : {"tradeId" : "?"}},{"field" : {"tradeTypeId" : "?"}} ]}}*/
    Trade findByTradeIdAndTradeTypeId(Long  tradeId,Integer tradeTypeId);

    /**{"bool" : {"must" : {"field" : {"tradeName" : {"query" : "?*","analyze_wildcard" : true}}}}}*/
    List<Trade> findByTradeNameLike(String  tradeName);

}
