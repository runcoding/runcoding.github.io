
### redis-sentinel 部署

https://hub.docker.com/r/bitnami/redis-sentinel/

```sh
-- 部署redis-sentinel
$ cd docker
$ docker-compose up -d
Starting docker_redis-master_1 ... done
Creating docker_redis-sentinel_1 ... done
Creating docker_redis-slave_1    ... done


$ docker ps
CONTAINER ID        IMAGE                  COMMAND                           PORTS                                NAMES
8dd5c2bace1e        bitnami/redis:latest   "/entrypoint.sh /run…"       0.0.0.0:32768->6379/tcp              docker_redis-slave_1
4485a6c6b70f        bitnami/redis:latest   "bash -c 'bash -s <<…"       6379/tcp, 0.0.0.0:16379->16379/tcp   docker_redis-sentinel_1
91a112a74b98        bitnami/redis:latest   "/entrypoint.sh /run…"        0.0.0.0:6379->6379/tcp              docker_redis-master_1

$ docker logs -f docker_redis-sentinel_1
11:X 30 Dec 07:50:32.638 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
11:X 30 Dec 07:50:32.638 # Redis version=4.0.12, bits=64, commit=00000000, modified=0, pid=11, just started
11:X 30 Dec 07:50:32.638 # Configuration loaded
11:X 30 Dec 07:50:32.643 * Running mode=sentinel, port=16379.
11:X 30 Dec 07:50:32.643 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
11:X 30 Dec 07:50:32.648 # Sentinel ID is f252078ad9efe3f7a9730453dc8ac3f85053eb4a
11:X 30 Dec 07:50:32.649 # +monitor master master-node 172.20.0.2 6379 quorum 2
11:X 30 Dec 07:50:42.692 * +slave slave 172.20.0.4:6379 172.20.0.4 6379 @ master-node 172.20.0.2 6379
11:X 30 Dec 07:50:52.752 * +fix-slave-config slave 172.20.0.4:6379 172.20.0.4 6379 @ master-node 172.20.0.2 6379
11:X 30 Dec 07:59:17.133 # +tilt #tilt mode entered
11:X 30 Dec 08:00:50.339 # +tilt #tilt mode entered
11:X 30 Dec 08:01:20.368 # -tilt #tilt mode exited

$ docker logs -f  docker_redis-master_1
1:M 30 Dec 08:00:50.340 # Disconnecting timedout slave: 172.20.0.4:6379
1:M 30 Dec 08:00:50.340 # Connection with slave 172.20.0.4:6379 lost.
1:M 30 Dec 08:00:50.660 * Slave 172.20.0.4:6379 asks for synchronization
1:M 30 Dec 08:00:50.660 * Partial resynchronization request from 172.20.0.4:6379 accepted. Sending 152 bytes of backlog starting from offset 20564.


$ docker logs -f docker_redis-slave_1
1:S 30 Dec 08:00:50.340 # Connection with master lost.
1:S 30 Dec 08:00:50.340 * Caching the disconnected master state.
1:S 30 Dec 08:00:50.651 * Connecting to MASTER 172.20.0.2:6379
1:S 30 Dec 08:00:50.652 * MASTER <-> SLAVE sync started
1:S 30 Dec 08:00:50.652 * Non blocking connect for SYNC fired the event.
1:S 30 Dec 08:00:50.657 * Master replied to PING, replication can continue...
1:S 30 Dec 08:00:50.660 * Trying a partial resynchronization (request af81a3d128b2462c374d4a2eabbdecd8b4b8b6e7:20564).
1:S 30 Dec 08:00:50.662 * Successful partial resynchronization with master.
1:S 30 Dec 08:00:50.662 * MASTER <-> SLAVE sync: Master accepted a Partial Resynchronization.

查看redis-master 的主从关系:
$ docker exec -it docker_redis-master_1  redis-cli -a laSQL2019 info Replication
# Replication
role:master
connected_slaves:1
slave0:ip=172.20.0.4,port=6379,state=online,offset=199291,lag=0
master_replid:af81a3d128b2462c374d4a2eabbdecd8b4b8b6e7
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:199291
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:199291


$ docker exec -it docker_redis-slave_1  redis-cli -a laSQL2019 info Replication
# Replication
role:slave
master_host:172.20.0.2
master_port:6379
master_link_status:up
master_last_io_seconds_ago:1
master_sync_in_progress:0
slave_repl_offset:210693
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:af81a3d128b2462c374d4a2eabbdecd8b4b8b6e7
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:210693
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:210693

停止 redis-master 容器,看是否会自动切换master ：
$ docker stop docker_redis-master_1
```