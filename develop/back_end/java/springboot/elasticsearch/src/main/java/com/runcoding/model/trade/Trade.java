package com.runcoding.model.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.runcoding.model.trade.order.TradeOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author runcoding
 * @date 2019-01-23
 * @desc: 交易信息
 * FieldType=  {
 *  Text, //text取代了string，当一个字段是要被全文搜索的,设置text类型以后，字段内容会被分析，在生成倒排索引以前，
 *          字符串会被分析器分成一个一个词项。text类型的字段不用于排序，很少用于聚合（termsAggregation除外）。
 * 	Integer,
 * 	Long,
 * 	Date,
 * 	Float,
 * 	Double,
 * 	Boolean,
 * 	Object,
 * 	Auto,
 * 	Nested, //嵌套类型
 * 	Ip,
 * 	Attachment, //附件类型
 * 	Keyword //类型适用于索引结构化的字段
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "#{@indexName}",
          type = "trade",
          shards = 4, /**节点数和Shard数相等时，ElasticSearch集群的性能可以达到最优。通常不推荐一个节点超过2个shard。*/
          replicas = 0, /**索引副本(Replica)机制的的思路很简单：为索引分片创建一份新的拷贝，它可以像原来的主分片一样处理用户搜索请求。
                          同时也顺便保证了数据的安全性。即如果主分片数据丢失，ElasticSearch通过索引副本使得数据不丢失。
                          索引副本可以随时添加或者删除，所以用户可以在需要的时候动态调整其数量。*/
        refreshInterval = "1s"/**自动1s刷新一次，-1不刷新(在大批量同步数据时使用)*/
)
/**动态模版映射模版*/
//@DynamicTemplates(mappingPath = "/mappings/test-dynamic_templates_mappings_two.json")
public class Trade {


    @ApiModelProperty("交易编号")
    @Id
    @Field(fielddata = true)
    private Long tradeId;

    @ApiModelProperty("用户编号")
    @Field(type = FieldType.Long)
    private Long userId;

    @ApiModelProperty("用户名称")
    /**https://github.com/medcl/elasticsearch-analysis-ik*/
    @Field( type = FieldType.Text,
            /**指定字段建立索引时指定的分词器*/
            analyzer="ik_max_word" ,
            /**指定字段搜索时使用的分词器*/
            searchAnalyzer = "ik_smart",
            /**默认情况下不存储原文*/
            store = true
    )
    private String userName;

    @ApiModelProperty("交易名称")
    @Field(type = FieldType.Text,analyzer="ik_max_word" , searchAnalyzer = "ik_max_word")
    private String tradeName;

    @ApiModelProperty("交易类型编号")
    @Field(type = FieldType.Long)
    private Long tradeTypeId;

    @ApiModelProperty("实付总金额")
    @Field(type = FieldType.Double,index = false)
    private BigDecimal totalRealAmount;

    @ApiModelProperty("交易状态")
    @Field(type = FieldType.Integer)
    private Integer tradeStatus;

    @ApiModelProperty("交易订单列表")
    @Field(type = FieldType.Nested)
    private List<TradeOrder> tradeOrders;

    @ApiModelProperty("交易创建时间")
    /**
     * https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-date-format.html
     * https://segmentfault.com/a/1190000016296983
     * */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("交易修改时间")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**地址位置 **/
    @GeoPointField
    private GeoPoint location;

}
