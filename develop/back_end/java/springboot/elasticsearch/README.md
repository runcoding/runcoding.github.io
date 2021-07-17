[](https://www.jianshu.com/p/7b23935e8b3e)


### productSkuName类型改变，导致启动失败 @Field(type = FieldType.keyword) 变成 @Field(type = FieldType.Text)
```log
Caused by: java.lang.IllegalArgumentException: mapper [tradeOrders.orderDetails.productSkuName] of different type,
 current_type [keyword], merged_type [text]
```
解决: 删除ES 索引重新创建

**一、Query查询器 与 Filter 过滤器**

**1.1 过滤器（filter）**

    通常用于过滤文档的范围，比如某个字段是否属于某个类型，或者是属于哪个时间区间
    * 创建日期是否在2014-2015年间？
    * status字段是否为success？
    * lat_lon字段是否在某个坐标的10公里范围内？
    
**1.2 查询器（query）** 

   使用方法像极了filter，但query更倾向于更准确的查找。 
    * 与full text search的匹配度最高
    * 正则匹配
    * 包含run单词，如果包含这些单词：runs、running、jog、sprint，也被视为包含run单词
    * 包含quick、brown、fox。这些词越接近，这份文档的相关性就越高
    查询器会计算出每份文档对于某次查询有多相关（relevant），然后分配文档一个相关性分数：_score。而这个分数会被用来对匹配了的文档进行相关性排序。相关性概念十分适合全文搜索（full-text search），这个很难能给出完整、“正确”答案的领域。
    query filter在性能上对比：filter是不计算相关性的，同时可以cache。因此，filter速度要快于query。
    
    
**二、查询match和term @See http://www.cnblogs.com/yjf512/p/4897294.html**

   **2.1 match 分词查询**
     比如"宝马多少马力"会被分词为"宝马 多少 马力", 所有有关"宝马 多少 马力", 那么所有包含这三个词中的一个或多个的文档就会被搜索出来。
  
   **2.2 match_phrase**
     精确匹配所有同时包含"宝马 多少 马力"的文档, 使用 "slop" : 1 少匹配一个也满足。
   
   **2.3 multi_match** 
     a. 两个字段进行匹配，其中一个字段有这个文档就满足
        {"multi_match":{"query":"我的宝马多少马力","fields":["title","content"]}}
     b.希望完全匹配的文档占的评分比较高，则需要使用best_fields。完全匹配"宝马 发动机"的文档评分会比较靠前，如果只匹配宝马的文档评分乘以0.3的系数
       {"multi_match":{"query":"我的宝马多少马力","fields":["title","content"],{"multi_match":{"query":"我的宝马多少马力","fields":["title","content"]}}}}
     c.越多字段匹配的文档评分越高，就要使用most_fields
        {"multi_match":{"query":"我的宝马发动机多少","type":"most_fields","fields":["tag","content"]}}
     d. 希望这个词条的分词词汇是分配到不同字段中的，那么就使用cross_fields
        {"multi_match":{"query":"我的宝马发动机多少","type":"cross_fields","fields":["tag","content"]}}
        
   **2.4 term是代表完全匹配**
   注意：使用term要确定的是这个字段是否“被分析”(analyzed)，默认的字符串是被分析的。创建索引时，对字符串字段设置不启用分词。
   
**三、bool联合查询: must,should,must_not**   
    3.1 must: 文档必须完全匹配条件
    3.2 should: should下面会带一个以上的条件，至少满足一个条件，这个文档就符合should
    3.3 must_not: 文档必须不匹配条件
    {"query":{"bool":{"must":{"term":{"content":"宝马"}},"must_not":{"term":{"tags":"宝马"}},"should":{"term":{"tags":"斑马"}}}}}