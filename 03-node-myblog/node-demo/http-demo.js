const http = require('http')
const querystring = require('querystring');

const server = http.createServer((req, res) => {
  const method = req.method
  const url = req.url
  const path = url.split('?')[0]
  const query = querystring.parse(url.split('?')[1])

  // 设置返还格式为 JSON
  res.setHeader('Content-type', 'application/json')

  // 定义返还数据
  const resDate = {
    method, url, path, query
  }

  // get 直接返回 json 字符串
  if (method === 'GET') {
    res.end(JSON.stringify(resDate))
  }
  // post 返回并且加上自己提交的数据
  if (method === 'POST') {
    let postData = '';
    req.on('data', chunk => {
      postData += chunk.toString()
    })
    req.on('end', () => {
      resDate.postData = postData;
      res.end(
        JSON.stringify(resDate)
      )
    })
  }
})

server.listen(3000)
