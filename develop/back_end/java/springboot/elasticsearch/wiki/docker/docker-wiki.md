# https://wizardforcel.gitbooks.io/mastering-elasticsearch/content/chapter-1/114_README.html

```docker
docker build  . -t runcoding_elasticsearch:6.4.3


```
[各类分词器](https://zhuanlan.zhihu.com/p/29183128)
### ik 分词(ik_max_word)
会将文本做最细粒度的拆分；尽可能多的拆分出词语
```sh
curl -XGET "http://192.168.43.58:9200/_analyze?pretty" -H 'Content-Type: application/json' -d'
  {
     "text":"中国浙江省宁波市交易","analyzer": "ik_max_word"
  }'
```  

返回

```json  
{
  "tokens": [
    {
      "token": "中国浙江",
      "start_offset": 0,
      "end_offset": 4,
      "type": "CN_WORD",
      "position": 0
    },
    {
      "token": "中国",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 1
    },
    {
      "token": "浙江省",
      "start_offset": 2,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 2
    },
    {
      "token": "浙江",
      "start_offset": 2,
      "end_offset": 4,
      "type": "CN_WORD",
      "position": 3
    },
    {
      "token": "省",
      "start_offset": 4,
      "end_offset": 5,
      "type": "CN_CHAR",
      "position": 4
    },
    {
      "token": "宁波市",
      "start_offset": 5,
      "end_offset": 8,
      "type": "CN_WORD",
      "position": 5
    },
    {
      "token": "宁波",
      "start_offset": 5,
      "end_offset": 7,
      "type": "CN_WORD",
      "position": 6
    },
    {
      "token": "市",
      "start_offset": 7,
      "end_offset": 8,
      "type": "CN_CHAR",
      "position": 7
    },
    {
      "token": "交易",
      "start_offset": 8,
      "end_offset": 10,
      "type": "CN_WORD",
      "position": 8
    }
  ]
}
```

### ik 分词(ik_smart)
会做最粗粒度的拆分；已被分出的词语将不会再次被其它词语占有
```sh
curl -XGET "http://192.168.43.58:9200/_analyze?pretty" -H 'Content-Type: application/json' -d'
  {
     "text":"中国浙江省宁波市交易","analyzer": "ik_smart"
  }'
```  
返回
```json
{
  "tokens": [
    {
      "token": "中国",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 0
    },
    {
      "token": "浙江省",
      "start_offset": 2,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 1
    },
    {
      "token": "宁波市",
      "start_offset": 5,
      "end_offset": 8,
      "type": "CN_WORD",
      "position": 2
    },
    {
      "token": "交易",
      "start_offset": 8,
      "end_offset": 10,
      "type": "CN_WORD",
      "position": 3
    }
  ]
}
```