const redis = require('redis')
const {REDIS_CONF} = require('../conf/db')

// 创建redis连接
const redisClient = redis.createClient(REDIS_CONF.port, REDIS_CONF.host)
redisClient.on('error', err => {
  console.log(err)
})

// redis 设置值
const set = (key, val) => {
  if (typeof val === 'object') {
    val = JSON.stringify(val)
  }
  redisClient.set(key, val, redis.print) // print 打印 set 后的日志。
}

// redis 获取值 返回 promise 对象
const get = (key) => {
  return new Promise((resolve, reject) => {
    redisClient.get(key, (err, val) => {
      if (err) {
        reject(err)
        return
      }
      if (val == null) {
        resolve(null)
        return;
      }
      try {
        resolve(
          JSON.parse(val)
        )
      } catch (e) {
        resolve(e)
      }
    })
  })
}

module.exports = {
  set,
  get
}
