// router 用来获取请求，并且匹配请求地址 把数据传给 controller

const {
  getList,
  getDetail,
  newBlog,
  updateBlog,
  delBlog
} = require('../controller/blog')
const {SuccessModel, ErrorModel} = require('../model/resModel')

const handleBlogRouter = (req, res) => {
  const method = req.method
  const id = req.query.id

  // 获取博客列表
  if (method === 'GET' && req.path === '/api/blog/list') {
    const author = req.query.author || ''
    const keyword = req.query.keyword || ''
    const result = getList(author, keyword)
    return result.then(listData => {
      return new SuccessModel(listData)
    })
    // const listData = getList(author, keyword)
    // return new SuccessModel(listData)
  }

  // 获取详情
  if (method === 'GET' && req.path === '/api/blog/detail') {
    const result = getDetail(id)
    return result.then(data => {
      return new SuccessModel(data)
    })
  }

  // 新增博客数据
  if (method === 'POST' && req.path === '/api/blog/new') {
    req.body.author = 'zhangsan' // 假数据
    const data = newBlog(req.body)
    return data.then(res => {
      return new SuccessModel(res)
    })
  }

  // 更新博客数据
  if (method === 'POST' && req.path === '/api/blog/update') {
    const result = updateBlog(id, req.body)
    return result.then(res => {
      if (res === true) {
        return new SuccessModel()
      } else {
        return new ErrorModel('更新失败')
      }
    })
  }

  // 删除博客数据
  if (method === 'POST' && req.path === '/api/blog/del') {
    const author = 'zhangsan' // 假数据
    const result = delBlog(id, author)
    return result.then(res => {
      if (res === true) {
        return new SuccessModel()
      } else {
        return new ErrorModel('更新失败')
      }
    })
  }

}

module.exports = handleBlogRouter
