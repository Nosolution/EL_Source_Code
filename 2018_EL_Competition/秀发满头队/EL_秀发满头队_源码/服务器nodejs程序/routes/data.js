const express = require('express')
const router = express.Router()
const sha1 = require('sha1')

const checkLogin = require('../middlewares/check').checkLogin
const PostModel = require('../models/posts')
const CommentModel = require('../models/comments')
const EventModel = require('../models/events')
const UserModel = require('../models/users')

// GET /data 所有用户或者特定用户的文章页
//   eg: GET /data?author=xxx
router.get('/', function (req, res, next) {
  const author = req.query.author

  EventModel.getEvents(author)
    .then(function (events) {
      var temp = new Array()
      for (var i=0 ; i<events.length && i<10 ; i=i+1) {
        temp[i] = events[i]
      }
      res.send(temp)
    })
    .catch(next)
})

// 获取特定用户文章
router.get('/:name', function (req, res, next) {
  const name = req.params.name

  UserModel.getUserByName(name)
    .then(function (user) {
      EventModel.getEvents(user._id)
        .then(function (events) {
          res.send(events)
        })
    })
    .catch(next)
})

// 创建一项活动
router.post('/create', checkLogin, function (req, res, next) {
  const author = req.session.user._id


  const content = req.fields.content
  const length = req.fields.length

  const year = req.fields.year
  const month = req.fields.month
  const day = req.fields.day

  const hour = req.fields.hour
  const min = req.fields.min
  //const location = req.fields.location


  let event = {
    author: author,
    content: content,
    length: length,
    year: year,
    month: month,
    day: day,
    hour: hour,
    min: min,
    //location: location
  }

  EventModel.create(event)
    .then(function (result) {
      post = result.ops[0]
      res.send({status: "success"})
    })
    .catch(next)
})

// GET /data/:eventId/remove 删除一篇活动
router.get('/:eventId/remove', checkLogin, function (req, res, next) {
  const eventId = req.params.eventId
  const author = req.session.user._id

  EventModel.getRawEventById(eventId)
    .then(function (event) {
      if (!event) {
        res.send({status: "error", error: "event does not exist"})
      }
      else if (event.author._id.toString() !== author.toString() ) {
        res.send({status: "error", error: "no permission"})
      }
      else {
        EventModel.delEventById(eventId)
        .then(function () {
          res.send({status: 'success'})
        })
      .catch(next)
    }
  })
})

//删除所有活动（需要密码）
router.get('/admin/:password/remove', function (req, res, next) {
  const password = req.params.password

  if (sha1(password) !== "6f205fdf8ca6fff1177eeb0790d437093fa92f83") {
    return res.send({status: 'error', error: 'no permission'})
  }
  else {
    EventModel.delAllEvents()
    .then(function () {
      res.send({status: 'success'})
    })
    .catch(next)
  }
})

// POST /data/:eventId/dolike 点赞一篇活动
router.get('/:eventId/dolike', checkLogin, function (req, res, next) {
  const eventId = req.params.eventId
  const author = req.session.user._id

  UserModel.getUserById(author)
    .then(function (user) {
      EventModel.getRawEventById(eventId)
        .then(function (event) {
          if (!event) {
            return res.send({status: "fail", error: "event does not exist"});
          }
          EventModel.incLike(eventId, user.name )
            .then(function () {
              res.send({status: "success"});
            })
            .catch(next)
        })
    })
})

// POST /data/:eventId/undolike 取消点赞一篇文章
router.get('/:eventId/undolike', checkLogin, function (req, res, next) {
  const eventId = req.params.eventId
  const author = req.session.user._id

  UserModel.getUserById(author)
  .then(function (user) {
    EventModel.getRawEventById(eventId)
  .then(function (event) {
    if (!event) {
      return res.send({status: "fail", error: "event does not exist"});
    }
    EventModel.decLike(eventId, user.name )
      .then(function () {
        res.send({status: "success"});
      })
      .catch(next)
    })
  })
})

// get event by date
router.post('/getEventsByDate', checkLogin, function (req, res, next) {
  const year = req.fields.year
  const month = req.fields.month
  const day = req.fields.day
  const author = req.session.user._id

  EventModel.getEventsByDate(author, year, month, day)
    .then(function (events) {
      res.send(events);
    })
})

module.exports = router


