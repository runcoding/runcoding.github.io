##
https://github.com/kubernetes/kubeadm


### 使用
```
安装
> brew install kubeadm

> kubeadm init # 创建一个 master节点

export KUBECONFIG=/etc/kubernetes/admin.conf

kubectl apply -f <network-of-choice.yaml>

kubeadm join --token <token> <master-ip>:<master-port>

```