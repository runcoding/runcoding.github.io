
## docker install
``` docker
docker pull flink:1.6-alpine
docker rm -f flink_local
docker run --name flink_local -d -p 8081:8081 -p 6123:6123 -v  ~/projects/service/flink/demo/quickstart/target:/opt/jar  -t flink:1.6-alpine local
docker logs -f --tail 200 flink_local
```
