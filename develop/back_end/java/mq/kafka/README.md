## 安装zookeepr

docker pull wurstmeister/zookeeper:latest
docker rm -f zookeeper
docker run -d --name zookeeper --publish 2181:2181   zookeeper:latest


## 安装kafka
docker pull wurstmeister/kafka:latest

docker rm -f kafka

docker run -d --name kafka \
--publish 9092:9092 \
--link zookeeper \
--env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
--env KAFKA_ADVERTISED_HOST_NAME=192.168.1.104 \
--env KAFKA_ADVERTISED_PORT=9092 \
wurstmeister/kafka:latest

docker logs -f kafka
docker exec -it kafka /bin/bash
 

## 安装kafka-manager
docker pull sheepkiller/kafka-manager

docker rm -f kafka-manager

docker run -it --name kafka-manager \
--rm  -p 9000:9000 \
--link zookeeper \
-e ZK_HOSTS=192.168.1.104:2181 \
-e APPLICATION_SECRET=letmein \
sheepkiller/kafka-manager



http://localhost:8080/kafka/send