## 一、RocketMQ基础
> 相关扩展阅读
 - [RocketMQ中文文档](http://rocketmq.cloud/zh-cn/docs/configuration-client.html)
 - [RocketMQ.apache](https://rocketmq.apache.org/)
 - [RocketMQ集群部署方式总结](http://www.leexide.com/post/RocketMQ集群部署方式总结)
 - [阿里云MQ使用文档](https://help.aliyun.com/document_detail/29532.html)
 - [消息重试](https://help.aliyun.com/document_detail/43490.html)
 - [消息幂等](https://help.aliyun.com/document_detail/44397.html)

## 二、源码分析

### client端分析 

#### 生产者

#### 消费者

##### 消息消费分配策略

##### 消息消费位点(offset)维护
- 模式一: **广播模式 MessageModel=BROADCASTING**
策略: **本地文件存储offset([LocalFileOffsetStore](https://github.com/apache/rocketmq/blob/master/client/src/main/java/org/apache/rocketmq/client/consumer/store/LocalFileOffsetStore.java))**
过程：
 - 项目在启动时(DefaultMQPullConsumerImpl)，初始化LocalFileOffsetStore后会调用load()方法。<br>
   从本地文件读取消费位点,path = (${rocketmq.client.localOffsetStoreDir} ? 'user.home') + ".rocketmq_offsets/192.168.0.200@DEFAULT/ConsumerGroupOne/offsets.json" <br>
   如果offsets.json不存在，从offsets.json.bak中读取，可能都为null。<br>
   offsets.json文件的内容时类OffsetSerializeWrapper包装的map。（ConcurrentMap<MessageQueue, AtomicLong> offsetTable =  new ConcurrentHashMap<MessageQueue, AtomicLong>();）<br>
   其中MessageQueue.java主要属性topic、brokerName、queueId<br>
   
 - 读取消费位点, readOffset(final MessageQueue mq, final ReadOffsetType type)<br>
   从内存中读取: ReadOffsetType=READ_FROM_MEMORY; 从ReadOffsetType=READ_FROM_STORE磁盘文件中读取
   
 - 将内存中的消息位点保存到磁盘persistAll(MessageQueue mq)  
   
 - 更新offset updateOffset(MessageQueue mq, long offset, boolean increaseOnly) 
```java
//LocalFileOffsetStore.java
private ConcurrentMap<MessageQueue, AtomicLong> offsetTable = new ConcurrentHashMap<MessageQueue, AtomicLong>();
@Override
public void updateOffset(MessageQueue mq, long offset, boolean increaseOnly) {
    if (mq != null) {
        AtomicLong offsetOld = this.offsetTable.get(mq);
        if (null == offsetOld) {
            offsetOld = this.offsetTable.putIfAbsent(mq, new AtomicLong(offset));
        }

        if (null != offsetOld) {
            if (increaseOnly) {
                /**增量变更offset,ps: 这里为啥不用已经包装好的 offsetOld.getAndAdd(offset) 方法来实现?*/
                MixAll.compareAndIncreaseOnly(offsetOld, offset);
            } else {
                /**全量变更(覆盖)offset*/
                offsetOld.set(offset);
            }
        }
    }
}
```


- 模式二: **集群模式 MessageModel=CLUSTERING**
策略: **远程Broker维护offset([RemoteBrokerOffsetStore](https://github.com/apache/rocketmq/blob/master/client/src/main/java/org/apache/rocketmq/client/consumer/store/RemoteBrokerOffsetStore.java))**
过程：
 - 读取消费位点, readOffset(final MessageQueue mq, final ReadOffsetType type)<br>
   1. ReadOffsetType=READ_FROM_MEMORY,从内存中读取; <br>
   2. ReadOffsetType=READ_FROM_STORE,从远程读取消费位点fetchConsumeOffsetFromBroker(mq),读取成功后调用updateOffset更新内存中的消费位点
   
```java
private long fetchConsumeOffsetFromBroker(MessageQueue mq) throws RemotingException, MQBrokerException,
        InterruptedException, MQClientException {
        /**
         1. 获取broker 地址，再从broker上获取消费位点
         2. broker有分master和slave上获取的问题
        */
        FindBrokerResult findBrokerResult = this.mQClientFactory.findBrokerAddressInAdmin(mq.getBrokerName());
        if (null == findBrokerResult) {

            this.mQClientFactory.updateTopicRouteInfoFromNameServer(mq.getTopic());
            findBrokerResult = this.mQClientFactory.findBrokerAddressInAdmin(mq.getBrokerName());
        }

        if (findBrokerResult != null) {
            QueryConsumerOffsetRequestHeader requestHeader = new QueryConsumerOffsetRequestHeader();
            requestHeader.setTopic(mq.getTopic());
            requestHeader.setConsumerGroup(this.groupName);
            requestHeader.setQueueId(mq.getQueueId());

            return this.mQClientFactory.getMQClientAPIImpl().queryConsumerOffset(
                findBrokerResult.getBrokerAddr(), requestHeader, 1000 * 5);
        } else {
            throw new MQClientException("The broker[" + mq.getBrokerName() + "] not exist", null);
        }
    }
```
   
 - 将内存中的消息位点保存到磁盘persistAll(MessageQueue mq)  
   
 - 更新offset updateOffset(MessageQueue mq, long offset, boolean increaseOnly)  

 - [开始是从master消费消息的，如果出现消费消费过慢的情况，consumer就会从slave（如果slave配置为可读）消费](https://www.cnblogs.com/sunshine-2015/p/8998549.html) 

### server端分析
 
#### namesrv

#### 消息存储

#### 集群部署

## 三、RocketMQ最佳实践
