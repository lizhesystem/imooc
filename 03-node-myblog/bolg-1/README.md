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
- cookie 简单应用于登录 验证，首先登录后服务端 `set-Cookie` 设置一个 `cookie` ，当客户端访问回来就就会在客户的 `cookie` 里生成一个cookie,
这个 `cookie`，客户端再请求的时候就会自动带着这个 `cookie` 过去。
当然默认你登录 服务端如果什么都不操作你是没有 cookie 的，cookie都是服务端给你设置的。 
    ```javascript
  // 服务端设置cookie,不能在客户端更改添加 httpOnly，防止恶意的在前端 js 修改cookie
  // 注意：如果不设置过期时间，默认1969年是表示无限期，httpOnly 会在浏览器显示个对勾。 
  res.setHeader('Set-Cookie', `userid=${userId}; path=/; httpOnly; expires=${getCookieExpires()}`) 
    ```
```javascript 1.8
```
- 设置超时时间

### 其他注意的点
1. 使用 session 会暴露 username 不安全。 
2. 使用userId  server 对象username 
3. 如果session存在内存中 比较大 会挤爆进程  node最大占用内存 3G左右  操作系统会限制一个进程最大可用内存 一直存在一个变量不合适

操作系统分配内存 开始--》结束地址 0x1122 0x333 ，一个内存中有堆 heap和栈 stack，  stack里放程序运行的变量，基础类型的变量，数字类型    heap里放引用类型变量，对象数组函数这些。 

pm2启动多个node.js  多个node.js访问一个redis  session放到redis里 （session访问频繁 对性能要求高。断电丢失重新登录，数据量不大）

流程--》
