## 常用命令

### 批量杀死进程
```
 /**批量kill nodejs 进程*/
 ps -ef | grep node | awk '{print $2}' | xargs kill -9
```

### 将所有行程以树状图显示
```
# mac 安装
$ brew install pstree

# pstree -g
```
