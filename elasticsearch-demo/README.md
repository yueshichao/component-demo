## Elasticsearch
版本：6.8.10

## 建立index、type，插入数据
```
PUT test

POST test/car/_mapping
{
  "properties": {
    "name": {
      "type": "keyword"
    },
    "price": {
      "type": "double"
    }
  }
}

POST test/car/_bulk
{ "index": {}}
{"name":"ford","price":2.22}
{ "index": {}}
{"name":"honda","price":1.22}
{ "index": {}}
{"name":"toyota","price":0.22}
{ "index": {}}
{"name":"bmw","price":100.22}
```