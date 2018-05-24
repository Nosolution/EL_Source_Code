'use strict';
const fs = require('fs')
const path = require('path')
const sha1 = require('sha1')
const express = require('express')
const router = express.Router()

const UserModel = require('../models/users')
const checkNotLogin = require('../middlewares/check').checkNotLogin

// GET /signup 注册页
router.get('/', function (req, res, next) {
  res.render('signup')
})

// POST /signup 用户注册
router.post('/', checkNotLogin, function (req, res, next) {
  const name = req.fields.name
  //const avatar = req.files.avatar.path.split(path.sep).pop()
  const avatar = req.fields.avatar
  let password = req.fields.password
  const repassword = req.fields.repassword

  // 明文密码加密
  password = sha1(password)

  // 待写入数据库的用户信息
  let user = {
    name: name,
    password: password,
    avatar: avatar,
  }
  // 用户信息写入数据库
  UserModel.create(user)
    .then(function (result) {
      user = result.ops[0]
      delete user.password// 删除密码这种敏感信息
      req.session.user = user
      res.send({status: "success", description: "sign up successfully"})
    })
    .catch(function (e) {
      if (e.message.match('duplicate key')) {
        return res.send({status: "fail", case: 1, description: "username has been used"})
      }
      next(e)
    })
})

module.exports = router
