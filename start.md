
## **本地启动服务**
### 安装node
- 下载：         https://nodejs.org/en/download/ 
- 安装淘宝npm：  npm install -g cnpm --registry=https://registry.npm.taobao.org
- 安装supervisor（类似热加载）：cnpm -g install supervisor
- 安装express(不是必须):   cnpm install -g express-generator@4
- 构建node-express项目(不是必须)： express node-express
###  进入node-express
- 安装依赖 cnpm install  
###  启动项目
  node-express/bin>  supervisor www 或 node www
###  访问项目
 localhost:3000
 
## ***部署简单远程服务*
### 使用heroku服务商部署
https://dashboard.heroku.com
### 项目
https://github.com/runcoding/runcoding-heroku
### 中间件
- redis   https://app.redislabs.com
- mongodb https://mongolab.com