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
  return exec(sql).then(res => {
    return res
  })
}

const getDetail = (id) => {
  const sql = `select * from blogs where id = '${id}'`
  return exec(sql).then(rows => {
    return rows[0]  // 结果是数组
  })
}

const newBlog = (blogData = {}) => {
  // blogData 是一个博客对象.
  const title = blogData.title
  const content = blogData.content
  const author = blogData.author
  const createTime = Date.now()
  const sql = `
    insert into blogs (title,content,author,createTime)
    values ('${title}','${content}','${author}','${createTime}')`
  return exec(sql).then(res => {
    return {id: res.insertId}  // insertId 是按每次插入返回的 id 值
  })
}

const updateBlog = (id, blogData = {}) => {
  // id 是更新博客 ID
  // blogData 是博客对象
  const title = blogData.title
  const content = blogData.content
  const sql = `
    update blogs set title = '${title}',content = '${content}' 
    where id = ${id}
  `
  return exec(sql).then(res => {
    return res.affectedRows > 0;
  })
}

const delBlog = (id, author) => {
  const sql = `delete from blogs where id = ${id} and author = '${author}';`
  return exec(sql).then(res => {
    return res.affectedRows > 0;
  })
}
module.exports = {
  getList,
  getDetail,
  newBlog,
  updateBlog,
  delBlog
}
