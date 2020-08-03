// controller 处理数据。
const {exec} = require('../db/mysql')

const getList = (author, keyword) => {
  // 创建 sql 使用 exec 执行。
  let sql = `select * from blogs where 1=1 `
  if (author) {
    sql += `and author = '${author}'`
  }
  if (keyword) {
    sql += `and title like '%${keyword}%'`
  }
  sql += ` order by createtime desc;`
  return exec(sql)
}

const getDetail = (id) => {
  return 'detail'
}

const newBlog = (blogData = {}) => {
  // blogData 是一个博客对象.
  console.log("blogData:" + Object.keys(blogData))
  return {
    id: 3
  }
}

const updateBlog = (id, blogData = {}) => {
  // id 是更新博客 ID
  // blogData 是博客对象
  console.log('更新' + id + '博客')
  return true
}

const delBlog = (id) => {
  return true
}
module.exports = {
  getList,
  getDetail,
  newBlog,
  updateBlog,
  delBlog
}
