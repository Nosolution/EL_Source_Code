const express = require('express')
const router = express.Router()
const sha1 = require('sha1')

const checkLogin = require('../middlewares/check').checkLogin
const EventModel = require('../models/events')
const UserModel = require('../models/users')
const TaskModel = require('../models/tasks')

// 所有任务
router.get('/', function (req, res, next) {

  TaskModel.getTasks()
    .then(function (tasks) {
      res.send(tasks)
    })
    .catch(next)
})

// 创建一项任务
router.get('/:target/create', checkLogin, function (req, res, next) {
  const target = req.params.target
  
  let task = {
      target: target,
  }
  
  TaskModel.create(task)
    .then(function (result) {
      post = result.ops[0]
      res.send({status: "success"})
    })
    .catch(next)
})

// 重设一项任务（会删除所有参加者）
router.get('/:target/reset', function (req, res, next) {
  const target = req.params.target

  TaskModel.getTaskByName("Daily Task")
    .then(function (task) {
      TaskModel.resetTask(task._id, Number(target))
        .then(function () {
          res.send({status: "success"})
        })
        .catch(next)
    })
})

// 删除所有任务（需要密码）
router.get('/admin/:password/remove', function (req, res, next) {
  const password = req.params.password

  if (sha1(password) !== "08375c8ee3d5b1b359fb0ae15f12557a693728da") {
    return res.send({status: 'error', error: 'no permission'})
  }
  else {
    TaskModel.delAllTasks()
    .then(function () {
      res.send({status: 'success'})
    })
    .catch(next)
  }
})

// 参加一项任务
router.get('/join', checkLogin, function (req, res, next) {
  const author = req.session.user._id
  const name = req.session.user.name
  
  UserModel.getUserById(author)
    .then(function (user) {
      TaskModel.getTaskByName("Daily Task")
        .then(function (task) {
          if (task && task.joinedusers && task.joinedusers.indexOf(name) !== -1 ) {
            return res.send({status: "error", error: "you have joined this task!"})
          }
          else if (task && task.finishedusers && task.finishedusers.indexOf(name) !== -1) {
            return res.send({status: "error", error: "you have joined this task!"})
          }
          else if (task && (user.like + user.credit < 10 )) {
            return res.send({status: "error", error: "your points are not enough!"})
          }
          else if (task){
            TaskModel.joinTask(task._id, name)
              .then(function () {
                UserModel.joinTask(author)
                  .then(function () {
                    res.send({status: "success"})
                  })
              })
              .catch(next)
          }
          else {
            return res.send({status: "error", error: "task does not exist"})
          }
        })
        .catch(next)
    })
})
    
    



module.exports = router