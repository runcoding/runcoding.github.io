## skywalking
[skywalking官网](https://github.com/apache/skywalking)
[skywalking downloads](https://skywalking.apache.org/zh/downloads/)
[](https://blog.csdn.net/smooth00/article/details/96479544)

```docker
# https://www.qingtingip.com/h_361971.html 
# 安装skywalking的服务(数据演示用h2，生产用es)
# https://hub.docker.com/r/apache/skywalking-oap-server
docker rm -f oap-server
docker run --name oap-server --restart always  -e TZ=Asia/Shanghai -p 11800:11800 -p 12800:12800 -d apache/skywalking-oap-server:6.2.0
docker logs -f --tail 200 oap-server

docker rm -f oap-server-es
docker run --name oap-server-es --restart always -d -e TZ=Asia/Shanghai -p 11800:11800 -p 12800:12800 -e SW_STORAGE=elasticsearch -e SW_STORAGE_ES_CLUSTER_NODES=192.168.0.200:9200 apache/skywalking-oap-server:6.2.0
docker logs -f --tail 200 oap-server-es

-- 安装skywalking的web UI
docker rm -f oap-ui
docker run --name oap-ui --restart always -d -p 8080:8080 -e TZ=Asia/Shanghai -e SW_OAP_ADDRESS=192.168.0.200:12800 apache/skywalking-ui:6.2.0
docker logs -f --tail 200 oap-ui
```

```shell script 
java  
-javaagent:/Users/runcoding/projects/service/skywalk/apache-skywalking-6.2.0/agent/skywalking-agent.jar
-Dskywalking.agent.service_name=maidao
-Dskywalking.collector.backend_service=127.0.0.1:11800
-jar app.jar 
```

### 核心原理
[使用ByteBuddy Java Agent 字节码增强](https://www.jianshu.com/p/fe1448bf7d31)