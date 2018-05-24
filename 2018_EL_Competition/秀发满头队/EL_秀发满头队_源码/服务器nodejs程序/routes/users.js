const express = require('express')
const router = express.Router()
const sha1 = require('sha1')

const checkLogin = require('../middlewares/check').checkLogin
const UserModel = require('../models/users')
const Eventmodel = require('../models/events')
const TaskModel = require('../models/tasks')

// 获取所有用户信息
router.get('/', function (req, res, next) {

    UserModel.getUsers()
      .then(function (users) {
        res.send(users)
      })
      .catch(next)
})

// 更改头像
router.get('/:name/:avatar/updateavatar', function (req, res, next) {
  const name = req.params.name
  const avatar = req.params.avatar

  UserModel.getUserByName(name)
    .then(function (user) {
      UserModel.updateAvatar(user, avatar)
        .then(function () {
          res.send({status: "success"})
        })
    })
    .catch(next)
})

// 获取用户信息，同时刷新
router.get('/:name/userinfo', checkLogin, function (req, res, next) {
  const name = req.params.name

  UserModel.getUserByName(name)
    .then(function (user) {
      Eventmodel.getEvents(user._id)
        .then(function (events) {
          var like = 0
          for (var i=0 ; i<events.length ; i=i+1) {
            if (!events[i].likedusers) {
              continue
            }
            like = like + events[i].likedusers.length
          }
          UserModel.updateLike(user, like)
            .then(function () {
              res.send(user)// 返回该用户信息
            })
        })
    })
    .catch(next)
})

// 更新位置信息
router.get('/:location/updatelocation', checkLogin, function (req, res,next) {
  const location = req.params.location
  const author = req.session.user._id

  UserModel.getUserById(author)
    .then(function (user) {
      UserModel.updatelocation(author, location)
       .then(function () {
         res.send({status: "success"})
       })
    })
    .catch(next)
})

// 根据完成的番茄数给用户排序
router.get('/rankbytomatoes', function (req, res, next) {

  UserModel.rankByTomatoes()
    .then(function (users) {
      res.send(users)
    })
    .catch(next)
})

// 根据得到的赞数给用户排序
router.get('/rankbylikes', function (req, res, next) {

  UserModel.rankByLikes()
    .then(function (users) {
      res.send(users)
    })
    .catch(next)
})

// 更新番茄数，并判断是否完成任务
router.get('/:name/:tomato/updatetomatoes', function (req, res, next) {
  const name = req.params.name
  const tomato = req.params.tomato

  UserModel.getUserByName(name)
    .then(function (user) {
      if (!user) {
        res.send({error: "user does not exist"})
      }
      else {
        UserModel.updateTomato(user._id, Number(tomato)) // 更新番茄数
          .then(function () {
            TaskModel.getTaskByName("Daily Task") // 搜索每日任务
              .then(function (task) {
                if (task && Number(tomato) >= Number(task.target) && (task.joinedusers.indexOf(name) !== -1) ) { // 判断是否完成任务
                  UserModel.finishTask(user._id)
                    .then(function () {
                      TaskModel.finishTask(task._id, user.name)
                        .then(function () {
                          res.send({status: "success", info: "you have finished the task!"})
                        })
                    })
                }
                else {
                  res.send({status: "success"})
                }
              })
          })
      }
    })
    .catch(next)
})

// 重设所有番茄数
router.get('/admin/:password/resetall', function (req, res, next) {
  const password = req.params.password

  UserModel.getUsers()
    .then(function (users) {
      if (sha1(password) !== "08375c8ee3d5b1b359fb0ae15f12557a693728da") {
        return res.send({status: 'error', error: 'no permission'})
      }
      else {
        for (var i=0 ; i<users.length ; i=i+1 ) {
          UserModel.updateTomato(users[i]._id, 0)
            .then(function () {
              res.send({ status: "success" })
            })
        }
      }
    })
    .catch(next)
  
})

// 根据用户 id 查找用户名
router.get('/:userId/findbyid', function (req, res, next) {
  const userId = req.params.userId

  UserModel.getUserById(userId)
    .then(function (user) {
      if (!user) {
        res.send({error: "user does not exist"})
      }
      else {
        res.send({user: user.name})
      }
    })
    .catch(next)
})

// 根据用户名查找用户 id
router.get('/:name/findbyname', function (req, res, next) {
  const name = req.params.name

  UserModel.getUserByName(name)
    .then(function (user) {
      if (!user) {
        res.send({error: "user does not exist"})
      }
      else {
        res.send({user: user._id})
      }
    })
    .catch(next)
})

// 删除所有用户（需要密码）
router.get('/admin/:password/remove', function (req, res, next) {
  const password = req.params.password

  if (sha1(password) !== "6f205fdf8ca6fff1177eeb0790d437093fa92f83") {
    res.send({status: 'error', error: 'no permission'})
  }
  else {
    UserModel.delAllUsers()
      .then(function () {
        res.send({status: 'success'})
      })
      .catch(next)
  }
})

// 删除用户
router.post('/remove', function (req, res, next) {
  const user = req.fields.user

  UserModel.remove(user)
    .then(function (user) {
      res.send("success")
    })
    .catch(next)
})

module.exports = router