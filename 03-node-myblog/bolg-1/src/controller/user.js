const {exec} = require('../db/mysql')
const loginCheck = (username, password) => {
  const sql = `
   select username,password from users where username = '${username}' and password = '${password}'
  `
  // select 返回的都是数组
  return exec(sql).then(res => {
    return res[0] || {}
  })
  // return username === 'lz' && password === '123';
}

module.exports = {
  loginCheck
}
