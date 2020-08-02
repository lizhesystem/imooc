const queryString = require('querystring')
const handleBlogRouter = require('./src/router/bolg')
const handleUserRouter = require('./src/router/user')

// 处理post data
const getPostData = (req) => {
  return new Promise(((resolve, reject) => {
    if (req.method !== 'POST') {
      resolve({})
      return
    }
    if (req.headers['content-type'] !== 'application/json') {
      resolve({})
      return
    }
    let postData = '';
    req.on('data', chunk => {
      postData += chunk.toString()
    })
    req.on('end', () => {
      if (!postData) {
        resolve({})
        return
      }
      resolve(
        JSON.parse(postData)
      )
    })
  }))
}


const serverHandle = ((req, res) => {
  // 设置返回格式 JSON
  res.setHeader('Content-type', 'application/json')

  // 处理请求参数，path 和 query 赋值给req
  const url = req.url
  req.path = url.split('?')[0]
  req.query = queryString.parse(url.split('?')[1])

  getPostData(req).then(postData => {
    req.body = postData

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



})

module.exports = serverHandle
