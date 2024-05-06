## 2 bug修复记录
### 2024.5.6
- 修复netty服务端无法接收客户端消息的bug（客户端的handler需要主动使用ctx.write()方法将数据写入channel）
