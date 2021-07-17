## 基本
- 列出运行的容器
docker ps  
- 删除运行的容器
docker rm -f "容器名称"   
- 进入容器
  - docker exec -it "容器名称" /bin/bash
  - sudo nsenter  --mount --uts --ipc --net --pid  --target 22177  
  其中22177为pid
  - docker attach "容器名称"
   
- 通过pid查看运行的容器
  docker ps -q | xargs docker inspect --format '{{.State.Pid}}, {{.Name}}' | grep "22177"  //其中22177为pid

- 通过端口查看被谁使用
  docker inspect $(docker ps -aq) | grep -C 30 '3306'

- 从容器中复制文件
  docker cp order-app:/root/data/sqlite/monitor/center.db /storage

- 容器删除网络桥接还在
 docker network disconnect -f host order-app

- 在容器中使用jdk工具(jmap、jinfo等)
-> docker run --cap-add=SYS_PTRACE  --name  order-app ……
## 构建运行

```Dockerfile
FROM rtfpessoa/ubuntu-jdk8:latest
MAINTAINER MaiBao "runcoding@163.com"
ADD target/app.jar .
```
docker pull runcoding:latest

docker run --cap-add=SYS_PTRACE \
--name runcodingapp \
--net=host \
-d -p 8080:8080 \
-v /storage/logs:/storage/logs \
runcoding:latest java -Xms1g -Xmx1g -jar app.jar --spring.profiles.active=release 

docker logs -f --tail 500 runcodingapp

## 网络
创建网络桥接
```sh 
docker network create -d bridge mysql-net
```
使用例子
```sh 
docker rm -f mysql-net1
docker run --name mysql-net1 --network mysql-net   -e MYSQL_ROOT_PASSWORD=mysql -d mysql:5.6   

docker rm -f mysql-net2
docker run --name mysql-net2 --network mysql-net   -e MYSQL_ROOT_PASSWORD=mysql -d mysql:5.6   

进入容器
> docker exec -it mysql-net1 /bin/bash
> apt-get update
> apt-get install iputils-ping

测试
> ping mysql-net2
PING mysql-net2 (172.18.0.3) 56(84) bytes of data.
64 bytes from mysql-net2.mysql-net (172.18.0.3): icmp_seq=1 ttl=64 time=0.526 ms
64 bytes from mysql-net2.mysql-net (172.18.0.3): icmp_seq=2 ttl=64 time=0.106 ms
```
## 系统命令
### 清理磁盘占用,注意会把容器和镜像都删除
```sh
 docker system prune -a
 
WARNING! This will remove:
        - all stopped containers
        - all networks not used by at least one container
        - all images without at least one container associated to them
        - all build cache
Are you sure you want to continue? [y/N]
```
## 允许状态图
![docker running status](https://yeasy.gitbooks.io/docker_practice/appendix/_images/cmd_logic.png)
