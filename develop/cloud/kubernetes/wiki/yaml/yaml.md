## yaml 文件

[yaml](../yaml/demo.yaml ':include')


### Pod 生命周期的变化
- Pending
  这个状态意味着，Pod 的 YAML 文件已经提交给了 Kubernetes，
  API 对象已经被创建并保存在 Etcd 当中。但是，这个 Pod 里有些容器因为某种原因而不能被顺利创建。比如，调度不成功。
- Running
  这个状态下，Pod 已经调度成功，跟一个具体的节点绑定。它包含的容器都已经创建成功，并且至少有一个正在运行中。
- Succeeded
  这个状态意味着，Pod 里的所有容器都正常运行完毕，并且已经退出了。这种情况在运行一次性任务时最为常见。
- Failed
  这个状态下，Pod 里至少有一个容器以不正常的状态（非 0 的返回码）退出。
  这个状态的出现，意味着你得想办法 Debug 这个容器的应用，比如查看 Pod 的 Events 和日志。
- Unknown
  这是一个异常状态，意味着 Pod 的状态不能持续地被 kubelet 汇报给 kube-apiserver，
  这很有可能是主从节点（Master 和 Kubelet）间的通信出现了问题。


