## 系统相关
### mac批量删除.DS_Store 文件
```sh
find ~/projects -name ".DS_Store" -exec rm -r "{}" \;
```

### 批量kill node
```sh 
ps -ef | grep node | awk '{print $2}' | xargs kill -9
-- 删除node 及其子进程
```

### 统计一个文件夹下单词的数量
```sh
>  egrep -o "\b[[:alpha:]]+\b" -r flink-java/** \
    |awk '{++count[$0]} END{for (word in count){ printf("%-20s,%d\n",word,count[word]);}}' \
    |sort -n -r -k2,2  -t ',' |head -200000 > alpha-c.csv
```

## Spring CLI
https://start.spring.io/

```
spring init  \
     --build maven  \
     --groupId com.runcoding  \
     --version 0.0.1-SNAPSHOT  \
     --java-version 1.8  \
     --dependencies web  \
     --name run  \
     --artifact springdemo \
     spring-boot-demo
```

## idea

