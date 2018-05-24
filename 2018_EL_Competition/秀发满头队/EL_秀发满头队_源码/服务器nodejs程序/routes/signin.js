const sha1 = require('sha1')
const express = require('express')
const router = express.Router()

const UserModel = require('../models/users')
const checkNotLogin = require('../middlewares/check').checkNotLogin

// GET /signin 登录页
router.get('/', checkNotLogin, function (req, res, next) {
  res.render('signin')
})

// POST /signin 用户登录
router.post('/', checkNotLogin, function (req, res, next) {
  const name = req.fields.name
  const password = req.fields.password

  UserModel.getUserByName(name)
    .then(function (user) {
      if (!user) {
        return res.send({status: "fail", case: 1, description: "user does not exist"});
      }
      if (sha1(password) !== user.password) {
        return res.send({status: "fail", case: 2, description: "username or password incorrect"});
      }
      delete user.password
      req.session.user = user
      res.send({status: "success", user: user});
    })
    .catch(next)
})

module.exports = router
