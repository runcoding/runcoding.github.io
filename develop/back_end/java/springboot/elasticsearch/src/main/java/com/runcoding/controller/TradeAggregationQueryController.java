package com.runcoding.controller;

import com.google.common.collect.Maps;
import com.runcoding.service.support.elastic.BusinessElasticsearchTemplate;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author runcoding
 *  聚合查询
 */
@Slf4j
@RestController
@RequestMapping(value = "/trade/aggregations/query")
public class TradeAggregationQueryController {

    @Resource
    private BusinessElasticsearchTemplate elasticsearchTemplate;



    @GetMapping("/aggregations")
    @ApiOperation(value = "聚合查询",notes = "指定交易时间内用户的订单数据,按天分组统计每天的交易总金额")
    public ResponseEntity getAggregations( @RequestParam(value = "userId",defaultValue = "100")String userId,
                                           @RequestParam(value = "startCreateTime",defaultValue = "2019-05-30 00:00:00")String startCreateTime,
                                           @RequestParam(value = "endCreateTime",defaultValue = "2019-06-25 23:59:59")String endCreateTime){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("createTime").gte(startCreateTime).lte(endCreateTime))
                .must(QueryBuilders.termQuery("userId",userId));

        // 聚合查询。totalRealAmount是要统计的字段，realAmount是自定义的别名
        SumAggregationBuilder sumAgg = AggregationBuilders.sum("realAmount").field("totalRealAmount");

        TermsAggregationBuilder groupDateAgg = AggregationBuilders.terms("createTime").field("createTime");
        /** 按日期date_histogram
         * DateHistogramAggregationBuilder dateAgg = AggregationBuilders
                .dateHistogram("createTime").field("createTime")
                .dateHistogramInterval(DateHistogramInterval.DAY)
                .subAggregation(sumAgg)
                ;*/
        groupDateAgg.subAggregation(sumAgg);

        TermsAggregationBuilder userAggregation =  AggregationBuilders.terms("userId").field("userId");
       // userAggregation.minDocCount(0L);
        userAggregation.subAggregation(sumAgg);
        userAggregation.subAggregation(groupDateAgg);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(userAggregation)
                .build();

        Map<String,Double> userGroupAmountMap = elasticsearchTemplate.query(searchQuery, response -> {
             Map<String,Double> userGroupMap = Maps.newConcurrentMap();
              response.getAggregations().asList().forEach(aggregation -> {
                  String name = aggregation.getName();
                   ((LongTerms) aggregation).getBuckets().forEach(bucket -> {
                       Object key = bucket.getKey();
                       long docCount = bucket.getDocCount();
                       bucket.getAggregations().forEach(agg -> {
                           if (agg instanceof  InternalSum){
                               double totalRealAmount = ((InternalSum) agg).getValue();
                               log.info("{}={},总交易数={},总金额={}",name,key,docCount,totalRealAmount);
                               return;
                           }
                           if (agg instanceof  LongTerms){
                               ((LongTerms) agg).getBuckets().forEach(createTimeAgg->{
                                   String createTime = createTimeAgg.getKeyAsString();
                                   createTimeAgg.getAggregations().forEach(aggAmount -> {
                                       if (aggAmount instanceof  InternalSum){
                                           double totalRealAmount = ((InternalSum) aggAmount).getValue();
                                           log.info("交易时间={},交易总金额={}",createTime,totalRealAmount);
                                           userGroupMap.put(createTime,totalRealAmount);
                                           return;
                                       }
                                   });
                               });
                           }

                       });
                   });
              });

            return userGroupMap;
        });
        return ResponseEntity.ok(userGroupAmountMap);
    }


    @GetMapping("/user/aggregations")
    @ApiOperation(value = "聚合查询",notes = "指定交易时间内用户的订单数据,按天分组统计每天的交易总金额")
    public ResponseEntity userAggregations(@RequestParam(value = "startCreateTime",defaultValue = "2019-05-30")String startCreateTime,
                                           @RequestParam(value = "endCreateTime",defaultValue = "2019-06-25")String endCreateTime){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("createTime").format("yyyy-MM-dd").gte(startCreateTime).lte(endCreateTime));
         //聚合查询。totalRealAmount是要统计的字段，realAmount是自定义的别名
         SumAggregationBuilder sumAgg = AggregationBuilders.sum("realAmount").field("totalRealAmount");

         DateHistogramAggregationBuilder dateAgg = AggregationBuilders
                 .dateHistogram("createTime").field("createTime").format("yyyy-MM-dd")
                 .dateHistogramInterval(DateHistogramInterval.DAY)
                 .subAggregation(sumAgg)
                 .minDocCount(0L)
                 .extendedBounds(new ExtendedBounds(startCreateTime,endCreateTime))
                 ;

        TermsAggregationBuilder userAggregation =  AggregationBuilders.terms("userId").field("userId");
        //userAggregation.subAggregation(sumAgg);
        userAggregation.subAggregation(dateAgg);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(userAggregation)
                .build();

        Map<String,Double> userGroupAmountMap = elasticsearchTemplate.query(searchQuery, response -> {

             return null;
        });

        return ResponseEntity.ok(userGroupAmountMap);
    }





}