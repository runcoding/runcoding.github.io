FROM docker.elastic.co/elasticsearch/elasticsearch:6.4.3

## 安装ik分词器
RUN mkdir -p /usr/share/elasticsearch/plugins/ik
RUN mkdir -p /usr/share/elasticsearch/plugins/pinyin
RUN mkdir -p /usr/share/elasticsearch/plugins/sql
RUN cd /usr/share/elasticsearch/plugins/ik && wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.4.3/elasticsearch-analysis-ik-6.4.3.zip && unzip elasticsearch-analysis-ik-6.4.3.zip
RUN cd /usr/share/elasticsearch/plugins/pinyin && wget https://github.com/medcl/elasticsearch-analysis-pinyin/releases/download/v6.4.3/elasticsearch-analysis-pinyin-6.4.3.zip && unzip elasticsearch-analysis-pinyin-6.4.3.zip
RUN cd /usr/share/elasticsearch/plugins/sql && wget https://github.com/NLPchina/elasticsearch-sql/releases/download/6.4.3.0/elasticsearch-sql-6.4.3.0.zip && unzip elasticsearch-sql-6.4.3.0.zip