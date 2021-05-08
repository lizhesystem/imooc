const fs = require('fs')
const path = require('path')


/**
 *  回调地狱 例子
 */
// function getFileContent(fileName, callback) {
//   const fullFileName = path.resolve(__dirname, 'files', fileName)
//   console.log(fullFileName) // 获取完整路径
//   fs.readFile(fullFileName, (err, data) => {
//     if (err) {
//       console.error(err)
//       return
//     }
//     callback(
//       JSON.parse(data.toString())
//     )
//   })
// }

// getFileContent('a.json', file => {
//   getFileContent(file.next, file1 => {
//     getFileContent(file1.next, file2 => {
//       console.log(file2)
//       // { next: 'c.json', msg: null }
//     })
//   })
// })


/**
 * promise 改写
 */
function getFileContent(fileName, callback) {
  const fullFileName = path.resolve(__dirname, 'files', fileName) // 获取完整路径
  return new Promise((resolve, reject) => {
    fs.readFile(fullFileName, (err, data) => {
      if (err) {
        reject(err)
      }
      resolve(JSON.parse(data.toString()))
    })
  })
}

// promise写法
async function getFile() {
  const file = await getFileContent('a.json')
  const file1 = await getFileContent(file.next)
  return await getFileContent(file1.next);
}

getFile().then(res => {
  console.log(res)
  // { next: 'c.json', msg: null }
})
