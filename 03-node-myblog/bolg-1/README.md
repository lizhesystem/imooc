# 使用原生的 `http` 实现简单的 `bolg` 项目

## 项目搭建
- 封装 `request` 和 `response` 接口。
  
  在 `app.js` 处理请求和返回结果。
- 封装 `router` 和 `controller`
  
  在 `controller` 里处理业务 数据库增删改操作。
  
  在 `router` 里处理过滤请求、参数，再拿到 `controller` 里数据的处理，最后进行统一返回。
  
## 数据 `mysql` 的封装以及简单的增删改查

   封装 `mysql` 执行统一 `sql` 的函数，在 `controller` 里使用。
   
## `cookie` 和 `session`
- 服务端设置cookie 不能在客户端更改 onlyxxx  
- 设置超时时间

使用 session 会暴露 username 不安全。 
使用userId  server 对象username 

如果session存在内存中 比较大 会挤爆进程  node最大占用内存 3G左右  操作系统会限制一个进程最大可用内存 一直存在一个变量不合适

操作系统分配内存 开始--》结束地址 0x1122 0x333 ，一个内存中有堆 heap和栈 stack，  stack里放程序运行的变量，基础类型的变量，数字类型    heap里放引用类型变量，对象数组函数这些。 

pm2启动多个node.js  多个node.js访问一个redis  session放到redis里 （session访问频繁 对性能要求高。断电丢失重新登录，数据量不大）

流程--》
