## Netty是什么

提到RPC框架会伴随着一个词语Netty,而Netty框架并不局限于RPC,更多的是作为一种网络协议的实现框架.由于RPC需要高效的网络通信,就可能选择以Netty作为基础,Netty十一个答疑看这里.

除了网络通信,RPC还需要有比较高效的序列化框架,以及一种寻址方式.如果是带会话（状态）的RPC调用,还需要有会话和状态保持的功能。

当然最主要的原因还是Netty有许多好处,比如对非阻塞IO（NIO）的支持,比如在链上传递时最大程度的减少buffer的copy（高性能）.

### Netty查看文档
- 《Netty 实战(精髓)》： https://waylau.gitbooks.io/essential-netty-in-action
- 《Netty 4.x 用户指南》 https://waylau.com/netty-4-user-guide/ (https://github.com/waylau/netty-4-user-guide)
-  Netty学习：https://github.com/code4craft/netty-learning