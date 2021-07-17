## Docker是什么
Docker是一个改进的容器技术。具体的“改进”体现在，Docker为容器引入了镜像，使得容器可以从预先定义好的模版（images）创建出来，并且这个模版还是分层的。

## Docker经常被提起的特点：
- 轻量，体现在内存占用小，高密度
- 快速，毫秒启动
- 隔离，沙盒技术更像虚拟机

## Docker技术的基础：
namespace，容器隔离的基础，保证A容器看不到B容器. 6个名空间：User,Mnt,Network,UTS,IPC,Pid
cgroups，容器资源统计和隔离。主要用到的cgroups子系统：cpu,blkio,device,freezer,memory
unionfs，典型：aufs/overlayfs，分层镜像实现的基础
