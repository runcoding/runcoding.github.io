## 使用wireshark抓包
[wireshark](https://www.cnblogs.com/TankXiao/archive/2012/10/10/2711777.html)


### mac 上抓本地服务的包
为什么wireshark抓包抓不到本机自己跟自己的通信包，因为本机发完本机的数据包不会经过网卡,
而是经过环回链路返回本机，如果要监听环路链路，wireshark需要监听Loopback:lo0

```sh
#监控本地 3307 端口的包，监听端口是lo0(回路), sql.pcap 文件可以用wireshark 分析
> tcpdump -i lo0 -s 3000 port 3307 -w sql.pcap

```