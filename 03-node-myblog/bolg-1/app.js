const handleBlogRouter = require('./src/router/bolg')
const handleUserRouter = require('./src/router/user')

const serverHandle = ((req, res) => {
  res.setHeader('Content-type', 'application/json')

  // 处理 blog 路由
  const blogData = handleBlogRouter(req, res)
  if (blogData) {
    res.end(
      JSON.stringify(blogData)
    )
  }
  // 处理 user 路由
  const userData = handleUserRouter(req, res)
  if (userData) {
    res.end(
      JSON.stringify(userData)
    )
  }

  // 路由未命中
  res.writeHead(404, {"Content-type": "text/plain"})
  res.write('404 not Found\n')
  res.end()

})

module.exports = serverHandle
