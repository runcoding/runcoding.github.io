 
## http://www.iocoder.cn/Spring-Security/OAuth2-learning/
### 客户端模式： 
 curl -X POST "http://localhost:8080/oauth/token" --user fooClientIdPassword:secret -d "grant_type=client_credentials&scope=foo%20read%20write%20openid"

```json
{"access_token":"62471a3a-7455-4d11-8dca-d19caac51fe1","token_type":"bearer","expires_in":35999,"scope":"openid","organization":"fooClientIdPasswordxQal"}
```
- 获取resource:
   curl  --header "Authorization:Bearer c3974f9d-a42e-4883-9c50-2bcc465cc7f9" http://localhost:9090/foos/1


### 密码模式(保留用户账号信息)：   
- 获取token：
 curl -X POST -u 'fooClientIdPassword:secret' http://localhost:8080/oauth/token -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d "grant_type=password&username=runcoding&password=runcoding&scope=foo%20read%20write%20openid"

```json
{"access_token":"5b19199e-5ad8-4343-9c18-2c7c8f968b80","token_type":"bearer","refresh_token":"c1cd722c-31fc-441c-8f3b-6926d2af6915","expires_in":35999,"scope":"foo","organization":"runcodingOyJk"}
```
- 获取resource:
   curl  --header "Authorization:Bearer c3974f9d-a42e-4883-9c50-2bcc465cc7f9" http://localhost:9090/foos/1

### 简化模式(implicit)： 
http://localhost:8080/oauth/authorize?client_id=fooClientIdPassword&redirect_uri=http://localhost:9999/dashboard/login&response_type=token&scope=openid

### 授权码模式(authorization_code): http://localhost:9999
####  获取code：  
   http://localhost:8080/oauth/authorize?client_id=fooClientIdPassword&redirect_uri=http://localhost:9999/dashboard/login&response_type=code&scope=openid

####  获取token： 
   curl -X POST --user fooClientIdPassword:secret http://localhost:8080/oauth/token -H "content-type: application/x-www-form-urlencoded" -d "code=8iiDIL&grant_type=authorization_code&redirect_uri=http://localhost:9999/dashboard/login&scope=openid"

### jwt 使用非对称加密(https://www.baeldung.com/spring-boot-https-self-signed-certificate)
https://juejin.im/post/5a043c59f265da430e4e9c82

生成证书：keytool -genkeypair -alias jwtkey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore jwtkey.p12 -validity 3650

keytool -list -rfc --keystore jwtkey.p12  | openssl x509 -inform pem -pubkey