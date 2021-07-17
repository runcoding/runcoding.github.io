## 使用Kubernetes发布应用
### create

```sh
$ kubectl create -f nginx-deployment.yaml
deployment.apps/nginx-deployment created
```
[yaml文件](yaml/nginx-deployment.yaml ':include :type=code')

查看运行结果

### get
```sh
$ kubectl get pods -l app=nginx

-l参数，即获取所有匹配 app: nginx 标签的 Pod
NAME                                     READY     STATUS    RESTARTS   AGE
nginx-deployment-75675f5897-gqhl8        1/1       Running   0          10m
nginx-deployment-75675f5897-tr4cx        1/1       Running   0          10m
可以看到有连个节点在执行
```

### describe
 使用 kubectl describe 命令，查看运行细节

```sh
$ kubectl describe pod nginx-deployment-75675f5897-gqhl8

Name:           nginx-deployment-75675f5897-gqhl8
Namespace:      default
Node:           docker-for-desktop/192.168.65.3
Start Time:     Fri, 28 Sep 2018 21:19:57 +0800
Labels:         app=nginx
                pod-template-hash=3123191453
Annotations:    <none>
Status:         Running
IP:             10.1.0.77
Controlled By:  ReplicaSet/nginx-deployment-75675f5897


Events:
  Type    Reason                 Age   From                         Message
  ----    ------                 ----  ----                         -------
  Normal  Scheduled              19m   default-scheduler            Successfully assigned nginx-deployment-75675f5897-gqhl8 to docker-for-desktop
  Normal  SuccessfulMountVolume  19m   kubelet, docker-for-desktop  MountVolume.SetUp succeeded for volume "default-token-5g96z"
  Normal  Pulling                19m   kubelet, docker-for-desktop  pulling image "nginx:1.7.9"
  Normal  Pulled                 19m   kubelet, docker-for-desktop  Successfully pulled image "nginx:1.7.9"
  Normal  Created                19m   kubelet, docker-for-desktop  Created container
  Normal  Started                19m   kubelet, docker-for-desktop  Started container
在 Kubernetes 执行的过程中，对 API 对象的所有重要操作，都会被记录在这个对象的 Events 里，并且显示在 kubectl describe 指令返回的结果中。
```

### replace
使用replace，更新yaml

```sh
$ kubectl replace -f nginx-deployment.yaml
deployment.apps/nginx-deployment replaced

```

### apply
推荐使用apply来部署，忽略使create还是replace。
```sh
$ kubectl apply  -f nginx-deployment.yaml
deployment.apps/nginx-deployment configured

```

### exec
使用 kubectl exec 指令，进入到这个 Pod 当中（即容器的 Namespace 中）查看这个 Volume 目录
```sh
$ kubectl exec -it nginx-deployment-7b5fff6d7d-zx7xm /bin/bash
root@nginx-deployment-7b5fff6d7d-zx7xm:/usr/share/nginx/html# ls
index.html #拷贝到宿主机的/Users/runcoding/Downloads/k8s目录下，在容器中就能看到

```

### delete
使用delete 从Kubernetes集群中删除部署的这个Nginx Deployment

```sh
$ kubectl delete  -f nginx-deployment.yaml
deployment.apps "nginx-deployment" deleted
请注意，我们不直接删除 pod。使用 kubectl 命令，我们要删除拥有该 pod 的 Deployment。如果我们直接删除pod，Deployment 将会重新创建该 pod。
``

### logs
```sh
$ kubectl logs -f --tail 200 nginx-deployment-7b5fff6d7d-d9l4s
$ kubectl logs --previous nginx-deployment-7b5fff6d7d-d9l4s #以前在 kubernetes 中执行的输出
```

### version
```
$ kubectl version
Client Version: version.Info{Major:"1", Minor:"11", GitVersion:"v1.11.1", GitCommit:"b1b29978270dc22fecc592ac55d903350454310a", GitTreeState:"clean", BuildDate:"2018-07-18T11:37:06Z", GoVersion:"go1.10.3", Compiler:"gc", Platform:"darwin/amd64"}
Server Version: version.Info{Major:"1", Minor:"10", GitVersion:"v1.10.3", GitCommit:"2bba0127d85d5a46ab4b778548be28623b32d0b0", GitTreeState:"clean", BuildDate:"2018-05-21T09:05:37Z", GoVersion:"go1.9.3", Compiler:"gc", Platform:"linux/amd64"}

```

### cluster-info
等同docker info
```sh
$ kubectl cluster-info
Kubernetes master is running at https://localhost:6443
KubeDNS is running at https://localhost:6443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

```

### run
```sh
## 创建容器
$ kubectl run --image=nginx nginx-app --port=80 --env="DOMAIN=cluster"
deployment.apps/nginx-app created

使用kubectl run 命令将创建一个名为 "nginx-app" 的 Deployment
## 删除运行的容器
$ kubectl delete deployment nginx-app
deployment.extensions "nginx-app" deleted
```

### 网络模式
> https://jimmysong.io/kubernetes-handbook/guide/accessing-kubernetes-pods-from-outside-of-the-cluster.html
#### hostNetwork
使用hostNotwork:true配置的话，在这种pod中运行的应用程序可以直接看到pod启动的主机的网络接口。
在主机的所有网络接口上都可以访问到该应用程序。
[yaml文件](yaml/network/hostnetwork.yaml ':include :type=code')

```sh
$ curl -v  http://localhost:8086/ping
*   Trying ::1...
* TCP_NODELAY set
* Connection failed
* connect to ::1 port 8086 failed: Connection refused

```
#### hostPort
hostPort是直接将容器的端口与所调度的节点上的端口路由，这样用户就可以通过宿主机的IP加上来访问
[yaml文件](yaml/network/hostport.yaml ':include :type=code')
```sh
$ kubectl apply  -f hostport.yaml
deployment.apps/nginx-hostport-deployment created

$ curl -v  http://localhost:8087/ping
*   Trying ::1...
* TCP_NODELAY set
* Connection failed
* connect to ::1 port 8087 failed: Connection refused
*   Trying 127.0.0.1...

```
#### NodePort
NodePort 服务是引导外部流量到你的服务的最原始方式。
NodePort，正如这个名字所示，在所有节点（虚拟机）上开放一个特定端口，任何发送到该端口的流量都被转发到对应服务。
![](http://dockone.io/uploads/article/20180409/d62babdf90dfd03c7bb7de7ea5207ee6.png)
[yaml文件](yaml/network/nodeport.yaml ':include :type=code')

缺点：
- 每个端口只能是一种服务
- 端口范围只能是 30000-32767
- 如果节点/VM 的 IP 地址发生变化，你需要能处理这种情况。

#### LoadBalancer
LoadBalancer 服务是暴露服务到 internet 的标准方式。
在 GKE 上，这种方式会启动一个 Network Load Balancer，它将给你一个单独的 IP 地址，转发所有流量到你的服务。
![](http://dockone.io/uploads/article/20180409/87dd7e06cfc7c1b4571d62bc838bd64d.png)
```sh
$ kubectl apply  -f loadbalancer.yaml
service/influxdb created

$ kubectl get svc influxdb
NAME       TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
influxdb   LoadBalancer   10.106.132.255   localhost     8086:32501/TCP   1m

外部可以用以下两种方式访问该服务：

- 使用任一节点的IP加30051端口访问该服务
- 使用EXTERNAL-IP来访问，这是一个VIP，是云供应商提供的负载均衡器IP，如localhost:8086。


$ curl -v  http://localhost:8086/ping
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8086 (#0)
> GET /ping HTTP/1.1
> Host: localhost:8086

```
这个方式的最大缺点是每一个用 LoadBalancer 暴露的服务都会有它自己的 IP 地址，每个用到的 LoadBalancer 都需要付费，这将是非常昂贵的。

#### Ingress
Ingress是自kubernetes1.1版本后引入的资源类型。必须要部署 Ingress controller 才能创建Ingress资源，Ingress controller是以一种插件的形式提供。Ingress controller 是部署在Kubernetes之上的Docker容器。它的Docker镜像包含一个像nginx或HAProxy的负载均衡器和一个控制器守护进程。控制器守护程序从Kubernetes接收所需的Ingress配置。它会生成一个nginx或HAProxy配置文件，并重新启动负载平衡器进程以使更改生效。换句话说，Ingress controller是由Kubernetes管理的负载均衡器。

Kubernetes Ingress提供了负载平衡器的典型特性：HTTP路由，粘性会话，SSL终止，SSL直通，TCP和UDP负载平衡等。
目前并不是所有的Ingress controller都实现了这些功能，需要查看具体的Ingress controller文档。
[yaml文件](yaml/network/ingress.yaml ':include :type=code')
外部访问URL http://influxdb.kube.example.com/ping 访问该服务，入口就是80端口，
然后Ingress controller直接将流量转发给后端Pod，不需再经过kube-proxy的转发，比LoadBalancer方式更高效。

总的来说Ingress是一个非常灵活和越来越得到厂商支持的服务暴露方式，
包括Nginx、HAProxy、Traefik，还有各种Service Mesh，而其它服务暴露方式可以更适用于服务调试、特殊应用的部署。


