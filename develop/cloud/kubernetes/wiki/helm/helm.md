## [Helm](https://helm.sh/)
Helm是一个kubernetes应用的包管理工具，用来管理charts——预先配置好的安装包资源，有点类似于Ubuntu的APT和CentOS中的yum。
Helm chart是用来封装kubernetes原生应用程序的yaml文件，可以在你部署应用的时候自定义应用程序的一些metadata，便与应用程序的分发。

### Helm和charts的主要作用：

应用程序封装
版本管理
依赖检查
便于应用程序分发

### 安装helm
```bash
# 用 homebrew 安装 Helm
$ brew install kubernetes-helm

# 初始化本地 CLI 并 将 Tiller 安装到 Kubernetes cluster
$ helm init
> helm init --upgrade -i registry.cn-hangzhou.aliyuncs.com/google_containers/tiller:v2.10.0 --stable-repo-url https://kubernetes.oss-cn-hangzhou.aliyuncs.com/charts
 注意版本匹配

```

### 使用一个chart
```
# 更新本地 charts repo
$ helm repo update

# 安装 mysql chart
$ helm install --name my-mysql stable/mysql

# 删除 mysql
$ helm delete my-mysql

# 删除 mysql 并释放该名字以便后续使用
$ helm delete --purge my-mysql
```

### 创建一个本地的nginx chart
```bash
$ helm create nginx
$ tree nginx
    nginx
    ├── Chart.yaml #Chart本身的版本和配置信息
    ├── charts #依赖的chart
    ├── templates #配置模板目录
    │   ├── NOTES.txt #helm提示信息
    │   ├── _helpers.tpl #用于修改kubernetes objcet配置的模板
    │   ├── deployment.yaml #kubernetes Deployment object
    │   └── service.yaml #kubernetes Serivce
    └── values.yaml #kubernetes object configuration
```
### 检查配置和模板是否有效
```bash
$ helm install --dry-run --debug nginx

```

### 使用helm将chart不是到k8s
``` bash

> helm install nginx/

# 指定本地chart压缩包：> helm install nginx-1.2.3.tgz
# 使用默认的远程仓库：> helm install stable/nginx
# 使用指定的仓库：> helm install localhost:8879/nginx-1.2.3.tgz


## 最后在当前命令行中输入下列命令，就可以绑定端口。进行访问
NOTES:
1. Get the application URL by running these commands:

> export POD_NAME=$(kubectl get pods --namespace default -l "app=nginx,release=deadly-bumblebee" -o jsonpath="{.items[0].metadata.name}")
  echo "Visit http://127.0.0.1:8080 to use your application"
  kubectl port-forward $POD_NAME 8080:80


```



### 查看部署的relaese
``` bash
$ helm list  # 多次部署会新增(所有使用helm部署的应用中如果没有特别指定chart的名字都会生成一个随机的Release name)
helm list
NAME               	REVISION	UPDATED                 	STATUS CHART        	APP VERSION	NAMESPACE
intended-guppy     	1       	Sat Sep 22 14:18:17 2018	DEPLOYEmongodb-0.1.0	1.0        	default
quarrelsome-octopus	1       	Sat Sep 22 14:21:00 2018	DEPLOYEmongodb-0.1.0	1.0        	default
saucy-manta        	1       	Sat Sep 22 14:23:21 2018	DEPLOYEmongodb-0.1.0	1.0        	default
```

#### 删除部署的release
``` bash
$ helm delete intended-guppy
release "intended-guppy" deleted

```

### 打包分享
```bash
> helm package  nginx/
Successfully packaged chart and saved it to: ……/helm/nginx-0.1.0.tgz

```
###  启用helm serve
> 查看打包的应用
``` bash
>  helm serve
Regenerating index. This may take a moment.
Now serving you on 127.0.0.1:8879

> curl http://localhost:8879

<html>
<h1>Helm Charts Repository</h1>
<ul>
  <li>nginx<ul>
    <li><a href="http://127.0.0.1:8879/nginx-0.1.0.tgz">nginx-0.1.0</a></li>
  </ul>
  </li>
</ul>
</html>

```

### 使用第三方chat库安装源
```bash
# 添加fabric8库
$helm repo add fabric8 https://fabric8.io/helm

# $helm repo add local http://localhost:8879  #添加本地源


# 搜索fabric8提供的工具（主要就是fabric8-platform工具包，包含了CI,CD的全套工具）
$helm search fabric8
NAME                                    CHART VERSION   APP VERSION     DESCRIPTION
fabric8/kibana                          2.2.327                         Awesome front-end for Elasticsearch


$ helm search local
NAME            CHART VERSION   APP VERSION     DESCRIPTION
local/nginx     0.1.0           1.0             A Helm chart for Kubernetes

……

### 依赖管理
有两种方式来管理chart的依赖。

直接在本的chart的charts目录下定义
通过在requirements.yaml文件中定义依赖的chart
在每个chart的charts目录下可以定义依赖的子chart。子chart有如下特点：

无法访问父chart中的配置
父chart可以覆盖子chart中的配置

```

## 参考
 - https://jimmysong.io/kubernetes-handbook/practice/helm.html

