const {loginCheck} = require('../controller/user')
const {SuccessModel, ErrorModel} = require('../model/resModel')
const {set} = require('../db/redis')


const handleUserRouter = (req, res) => {
  const method = req.method

  // 登录
  if (method === 'POST' && req.path === '/api/user/login') {
    const {username, password} = req.body
    const result = loginCheck(username, password)
    return result.then(res => {
      if (res.username) {
        req.session.username = res.username
        req.session.realname = res.realname
        // 同步到 redis
        set(req.sessionId, req.session)
        return new SuccessModel()
      }
      return new ErrorModel('登录失败')

    })
  }
}

module.exports = handleUserRouter
