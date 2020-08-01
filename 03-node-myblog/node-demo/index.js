const http = require('http')
const querystring = require('querystring');


const server = http.createServer((req, res) => {
  // get请求
  if (req.method === 'GET') {
    console.log('method:', req.method)
    const url = req.url
    console.log('url:', url)
    // 拿到请求数据后使用 queryString 解析成json
    req.query = querystring.parse(url.split('?')[1])
    console.log(req.query)
    res.end(json.stringify(req.query))
    // post请求
  } else if (req.method === 'POST') {
    console.log('content-type', req.headers['content-type']) // json
    let postDate = ''
    req.on('data', chunk => {
      postDate += chunk.toString()
    })
    req.on('end', () => {
      console.log(postDate)
      res.end('hello word')
    })
  }

})

server.listen(3000)

// get请求:http://127.0.0.1:3000/?name=1
// method: GET
// url: /?name=1
//   [Object: null prototype] { name: '1' }
// method: GET
// url: /favicon.ico
//   [Object: null prototype] {}


// get请求：使用 application/json 类型
// content-type application/json
// {
//   "name":"李哲",
//   "age":22
// }
