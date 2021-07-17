## 脚本规则引擎
[QLExpress vs mvel](http://www.welkinbai.com/2019/05/30/ql-intro/)
[美团](https://mp.weixin.qq.com/s/E-9GR0Mun1pudC0V1nXCsg)

### 轻量级
- [aviator](https://github.com/killme2008/aviator/wiki)
   aviator 支持jdk8 lambda 表达式

- [阿里巴巴QLExpress](https://github.com/alibaba/QLExpress)
  QLExpress 不支持jdk8 lambda 表达式

- [mvel](http://mvel.documentnode.com/#simple-property-expression)
  适用于极简单的规则，一般不推荐
  
- [easy-rules](https://github.com/j-easy/easy-rules)
  - 首先集成了mvel表达式，后续可能集成SpEL的一款轻量级规则引擎
  - 基于POJO,主要的注解：@Action,@Condition,@Fact,@Priority,@Rule
  
### 较重量级-生成字节码文件
- [groovy](https://github.com/apache/groovy)


### 重量级
- [Drools]()