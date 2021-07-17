package com.runcoding.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.runcoding.model.trade.Trade;
import com.runcoding.service.support.elastic.BusinessElasticsearchTemplate;
import com.runcoding.service.support.elastic.query.BusinessNativeSearchQuery;
import com.runcoding.service.support.elastic.query.BusinessNativeSearchQueryBuilder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.util.CloseableIterator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @author runcoding
 * - [Elasticsearch 权威指南（中文版）](https://es.xiaoleilu.com/index.html)
 * - https://n3xtchen.github.io/n3xtchen/elasticsearch/2017/07/05/elasticsearch-23-useful-query-example
 * - https://zq99299.github.io/note-book/elasticsearch-senior/java-api/88-full-text.html
 * - https://zq99299.github.io/note-book/elasticsearch-core/
 * - https://blog.csdn.net/tianyaleixiaowu/article/details/77965257
 * - https://github.com/spring-projects/spring-data-elasticsearch/blob/master/src/test/java/org/springframework/data/elasticsearch/core/ElasticsearchTemplateTests.java
 * -  深度分页es过程：
 *    - http://tech.dianwoda.com/2017/09/21/elasticsearchshen-du-fen-ye/
 *    - http://arganzheng.life/deep-pagination-in-elasticsearch.html
 */
@Slf4j
@RestController
@RequestMapping(value = "/trade/query")
public class TradeQueryController {

    @Resource
    private BusinessElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("/queryUserIdAndProductSkuId")
    @ApiOperation("精确匹配用户下sku查询交易")
    public ResponseEntity<Page<Trade>> queryUserIdAndProductSkuId(
                                                 @RequestParam(value = "userId",defaultValue = "100")String userId,
                                                 @RequestParam(value = "orderNumber",defaultValue = "77290779")String orderNumber,
                                                 @RequestParam(value = "productSkuId",defaultValue = "66")String productSkuId,
                                                 @RequestParam(value = "tradeTypeId",defaultValue = "1")String tradeTypeId,
                                                 @RequestParam(value = "tradeStatus",defaultValue = "0")String tradeStatus){
        /**嵌套属性*/
        NestedQueryBuilder nestedOrderNumberQuery = QueryBuilders.nestedQuery("tradeOrders", new QueryStringQueryBuilder(orderNumber).field("tradeOrders.orderNumber"), ScoreMode.Avg);
        /**多级嵌套属性*/
        NestedQueryBuilder nestedProductSkuIdQuery = QueryBuilders.nestedQuery("tradeOrders.orderDetails", new QueryStringQueryBuilder(productSkuId).field("tradeOrders.orderDetails.productSkuId"), ScoreMode.Avg);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                 .must(new QueryStringQueryBuilder(tradeStatus).field("tradeStatus"))
                 .must(new QueryStringQueryBuilder(tradeTypeId).field("tradeTypeId"))
                 .must(nestedOrderNumberQuery)
                 .must(nestedProductSkuIdQuery)
                 .must(new QueryStringQueryBuilder(userId).field("userId"))
                ;

        BusinessNativeSearchQuery searchQuery = new BusinessNativeSearchQueryBuilder()
                //手动指定索引版本，trade_v2，trade_v3 或trade_v* 或 trade_alias(别名)
                .withIndices("trade_alias")
                .withFilter(boolQuery).withQuery(matchAllQuery())
                .withSort(new FieldSortBuilder("tradeId").order(SortOrder.ASC))
                .withPageable(PageRequest.of(0, 5))
                /**search-after 前一次查询排序后最后的排序值，多个用','分开。适合实时分页*/
                //.withSearchAfter(10008996)
                .build();

        long count = elasticsearchTemplate.count(searchQuery,Trade.class);
        log.info("用户和sku查询交易数量={}",count);

        List<Trade> trades = elasticsearchTemplate.queryForList(searchQuery, Trade.class);
        log.info("获取数据trades={}",JSON.toJSONString(trades));

        /**
         * scroll查询原理是在第一次查询的时候一次性生成一个快照，根据上一次的查询的id来进行下一次的查询，这个就类似于关系型数据库的游标，
         * 然后每次滑动都是根据产生的游标id进行下一次查询，这种性能比上面说的分页性能要高出很多，基本都是毫秒级的。
         * 注意：
         *  1. scroll不支持跳页查询。 使用场景：对实时性要求不高的查询，例如微博或者头条滚动查询。
         *  2. rpc 调用重试与超时问题，都会导致数据不准确
         * */
        ScrolledPage<Trade> scrolledPage = (ScrolledPage<Trade>) elasticsearchTemplate.startScroll(60000, searchQuery, Trade.class);

        log.info("获取数据scrolledPage={}",JSON.toJSONString(scrolledPage));
        while (scrolledPage.hasContent()){
            /**通过快照滚动下一页*/
            scrolledPage = (ScrolledPage<Trade>)elasticsearchTemplate.continueScroll(scrolledPage.getScrollId(), 60000, Trade.class);
            log.info("ScrollId={},获取数据scrolledPage={}",scrolledPage.getScrollId(),JSON.toJSONString(scrolledPage));
        }
        /**删除快照*/
        elasticsearchTemplate.clearScroll(scrolledPage.getScrollId());


        CloseableIterator<Trade> streamTradeList = elasticsearchTemplate.stream(searchQuery, Trade.class);

        log.info("获取数据streamTradeList={}",JSON.toJSONString(streamTradeList));

        /**search-after 会生效，进行排序后过滤*/
        Page<Trade> page = elasticsearchTemplate.queryForPage(searchQuery, Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/match/tradeName")
    @ApiOperation("模糊查询交易")
    public ResponseEntity<Page<Trade>> matchQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交易")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));

        /**
         * "match"分词查询
         * 比如"千岛湖交易1200"会被分词为"千岛湖 交易 1200"那么所有包含这三个词中的一个或多个的文档就会被搜索出来。
         * 假如:
         *  1. tradeName = "千岛湖交易" ,那么会把含有"交易"也都查询出来(默认Operator.OR)
         *  2. tradeName = "千岛湖",只会把含有"千岛湖"查询出来
         *  https://www.cnblogs.com/reycg-blog/p/10002794.html
         * */
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("tradeName", tradeName);
        matchQueryBuilder.operator(Operator.AND);
        //lenient 默认值是 false，表示用来在查询时如果数据类型不匹配且无法转换时会报错。如果设置成 true 会忽略错误。
        matchQueryBuilder.lenient(false);
        //fuzziness 参数可以使查询的字段具有模糊搜索的特性,例如，查找名字Smith时，就会找出与之相似的Smithe， Smythe， Smyth， Smitt等。
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        //prefix_length 表示不能没模糊化的初始字符数。由于大部分的拼写错误发生在词的结尾，而不是词的开始，使用 prefix_length 就可以完成优化
        matchQueryBuilder.prefixLength(3);
        //cutoff_frequency 1: 指定为一个分数(0.01)表示出现频率,2: 指定为一个正整数(5)则表示出现次数。
        matchQueryBuilder.cutoffFrequency(2);
        //支持同义词
        matchQueryBuilder.autoGenerateSynonymsPhraseQuery(true);
        // matchQueryBuilder.analyzer("search analyzer"); //分词器
        String matchQuery = matchQueryBuilder.toString();
        log.info("matchQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        log.info("match模糊查询(分词):={}",JSON.toJSONString(page, SerializerFeature.PrettyFormat));
        return ResponseEntity.ok(page);
    }


    @GetMapping("/match/phrase/tradeName")
    @ApiOperation("模糊短语查询")
    public ResponseEntity<Page<Trade>> phraseQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交易")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));
        MatchPhraseQueryBuilder phraseQueryBuilder = QueryBuilders.matchPhraseQuery("tradeName", tradeName);

        phraseQueryBuilder.zeroTermsQuery(MatchQuery.ZeroTermsQuery.ALL);
        /**短语匹配支持跳过<=2个词的内容*/
        phraseQueryBuilder.slop(2);
        String matchQuery = phraseQueryBuilder.toString();
        log.info("matchQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        /**
         * query = {"query":{"wrapper":{"query":"ewogICJtYXRjaF9waHJhc2UiIDogewogICAgInRyYWRlTmFtZSIgOiB7CiAgICAgICJxdWVyeSIgOiAi56S+5ZWGIiwKICAgICAgInNsb3AiIDogMiwKICAgICAgInplcm9fdGVybXNfcXVlcnkiIDogIkFMTCIsCiAgICAgICJib29zdCIgOiAxLjAKICAgIH0KICB9Cn0="}}}
         * 其中"query":"ewogICJtYXRj……" 为base64编码
         * 解析后语句: {"query":{"wrapper":{"query":{"match_phrase":{"tradeName":{"query":"社商","slop":2,"zero_terms_query":"ALL","boost":1}}}}}}
         *
         * match_phrase 短语匹配,严格按照查询词的顺序,类似mysql '%社商%' ,但是可以通过slop跳过几个词来匹配出"社交电商"的内容。
         * */
        log.info("matchPhrase(短语)查询:="+ JSON.toJSONString(page, SerializerFeature.PrettyFormat));

        MatchPhrasePrefixQueryBuilder prefixQueryBuilder = QueryBuilders.matchPhrasePrefixQuery("tradeName", tradeName)
                .slop(1)
                //max_expansions参数，该参数可以控制最后项将要被扩展的前缀的个数。
                .maxExpansions(10);
         matchQuery = prefixQueryBuilder.toString();
        log.info("matchQuery={}",matchQuery);
        page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/match/multi/tradeName")
    @ApiOperation("多属性分词查询")
    public ResponseEntity<Page<Trade>> multiMatchQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交易")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(tradeName,
                "tradeName" , "tradeOrders.orderDetails.productSkuName")
                /**分词后评分越高的排在前面*/
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                /***越多字段匹配的评分越高，在商品搜索时使用*/
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                /**希望这个词条的分词词汇是分配到不同字段中的*/
                .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS);

        String matchQuery = multiMatchQuery.toString();
        log.info("matchQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/term/tradeName")
    @ApiOperation("分词查询")
    public ResponseEntity<Page<Trade>> termQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交易")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));
        //分词精确查询
        TermQueryBuilder termQuery = QueryBuilders.termQuery("tradeName", tradeName);
        String matchQuery = termQuery.toString();
        log.info("matchQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/term/common/tradeName")
    @ApiOperation("高频词查询")
    public ResponseEntity<Page<Trade>> termCommonQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交易")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));
        //分词精确查询
        CommonTermsQueryBuilder commonTermsQuery = QueryBuilders.commonTermsQuery("tradeName", tradeName);
        commonTermsQuery.cutoffFrequency(Float.valueOf("0.1"));
        String matchQuery = commonTermsQuery.toString();
        log.info("commonTermsQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/prefix/tradeName")
    @ApiOperation("前缀查询")
    public ResponseEntity<Page<Trade>> prefixQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));
        //前缀查询
        PrefixQueryBuilder prefixQuery = QueryBuilders.prefixQuery("tradeName", tradeName);
        String matchQuery = prefixQuery.toString();
        log.info("prefixQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/fuzzy/tradeName")
    @ApiOperation("模糊查询")
    public ResponseEntity<Page<Trade>> fuzzyQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));
        //前缀查询
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("tradeName", tradeName);
        String matchQuery = fuzzyQuery.toString();
        log.info("fuzzyQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }


    @GetMapping("/range/tradeName")
    @ApiOperation("范围查询")
    public ResponseEntity<Page<Trade>> rangeQueryByTradeName(@RequestParam(value = "qt",defaultValue = "100")Integer qt,
                                                             @RequestParam(value = "lt",defaultValue = "103")Integer lt){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));

        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("userId")
        //大于100 小于103
        .gt(qt).lt(lt)
        //包含上下界限
        .includeUpper(true).includeLower(true);
        String matchQuery = rangeQuery.toString();
        log.info("rangeQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/wildcard/tradeName")
    @ApiOperation("通配符查询('*' : 任意字符串; '?':任意一个字符)")
    public ResponseEntity<Page<Trade>> wildcardQueryByTradeName(@RequestParam(value = "tradeName",defaultValue = "交*")String tradeName){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));

        WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("tradeName", tradeName);
        String matchQuery = wildcardQuery.toString();
        log.info("wildcardQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }


    @GetMapping("/ids/tradeName")
    @ApiOperation("id查询")
    public ResponseEntity<Page<Trade>> idsQueryByTradeName(@RequestParam(value = "id",defaultValue = "41686654")String id){
        Pageable pageable =   PageRequest.of(0,10);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "tradeId"));

        IdsQueryBuilder idsQuery = QueryBuilders.idsQuery();
        idsQuery.addIds(id);
        String matchQuery = idsQuery.toString();
        log.info("idsQuery={}",matchQuery);
        Page<Trade> page = elasticsearchTemplate.queryForPage(new StringQuery(matchQuery, pageable,sort), Trade.class);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/geoPoint/nearby")
    @ApiOperation("交易地理位置查询-杭州某个点附近160.3KM的交易")
    public ResponseEntity<Page<Trade>> geoPointNearbyQuery(){
        /**左上(南京)*/
        GeoPoint topLeftPoint     = new GeoPoint(  32.061557,118.758312);
        /**右下(宁波)*/
        GeoPoint bottomRightPoint = new GeoPoint( 29.795295,121.614758);

        /**构建查询单元*/
        Criteria criteria = new Criteria();
        criteria.and(
                /**模糊查询，会分词。注意分词*/
                new Criteria("userName").contains("张"),
                /**用户id在[100,105]之间*/
                new Criteria("userId").between(100,105),
                /**查询值必须已张"三"结尾。注意分词*/
                //new Criteria("userName").endsWith("三"),
                /**距离过滤，在左上角和右下角所画的框内的点*/
                //new Criteria("location").boundedBy(topLeftPoint,bottomRightPoint),
                /**计算距离用户坐标：杭州(30.2756, 120.197521)，相距160.3km以内的城市(上海在此处之内)。*/
                new Criteria("location").within( new GeoPoint(30.2756, 120.197521), "160.3km")
        );
        CriteriaQuery query = new CriteriaQuery(criteria);
        Sort orders = Sort.by(
                /**根据创建时间，降序排序*/
                new Sort.Order(Sort.Direction.DESC, "createTime"),
                /**根据id，生序排序*/
                new Sort.Order(Sort.Direction.ASC, "tradeId")
        );

        query.addSort(orders);
        log.info("交易地理位置查询={}",query.toString());
        Page<Trade> page = elasticsearchTemplate.queryForPage(query, Trade.class);
        return ResponseEntity.ok(page);
    }



}