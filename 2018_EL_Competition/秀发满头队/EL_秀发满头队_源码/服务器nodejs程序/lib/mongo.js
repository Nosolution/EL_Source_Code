const config = require('config-lite')(__dirname)
const Mongolass = require('mongolass')
const mongolass = new Mongolass()
mongolass.connect(config.mongodb)

exports.User = mongolass.model('User', {
  name: { type: 'string', required: true },
  password: { type: 'string', required: true },
  avatar: { type: 'string'/*, required: true*/ },
  friends: [{ type: Mongolass.Types.ObjectId}],
  like: { type: 'number', default: 0},// 用户被点赞数
  credit: { type: 'number', default: 10},// like 与 credit 之和为积分
  tomato: { type: 'number', default: 0},// 用户完成的番茄数，每天 0 点重置
  location: { type: 'string', default: "32.1124200000,118.9604300000" },
})
exports.User.index({ name: 1 }, { unique: true }).exec()// 根据用户名找到用户，用户名全局唯一

const moment = require('moment')
const objectIdToTimestamp = require('objectid-to-timestamp')

// 根据 id 生成创建时间 created_at
mongolass.plugin('addCreatedAt', {
  afterFind: function (results) {
    results.forEach(function (item) {
      item.created_at = moment(objectIdToTimestamp(item._id)).format('YYYY-MM-DD HH:mm')
    })
    return results
  },
  afterFindOne: function (result) {
    if (result) {
      result.created_at = moment(objectIdToTimestamp(result._id)).format('YYYY-MM-DD HH:mm')
    }
    return result
  }
})

exports.Post = mongolass.model('Post', {
  author: { type: Mongolass.Types.ObjectId, required: true },
  title: { type: 'string', required: true },
  content: { type: 'string', required: true },
  pv: { type: 'number', default: 0 },
  like: { type: 'number', default: 0},
})
exports.Post.index({ author: 1, _id: -1 }).exec()// 按创建时间降序查看用户的文章列表

exports.Comment = mongolass.model('Comment', {
  author: { type: Mongolass.Types.ObjectId, required: true },
  content: { type: 'string', required: true },
  postId: { type: Mongolass.Types.ObjectId, required: true }
})
exports.Comment.index({ postId: 1, _id: 1 }).exec()// 通过文章 id 获取该文章下所有留言，按留言创建时间升序

// create an event
exports.Event = mongolass.model('Event', {
  author: { type: Mongolass.Types.ObjectId, required: true },
  content: { type: String, required: true },
  length: { type: String, required: true},
  year: { type: String, required: true},
  month: { type: String, required: true},
  day: { type: String, required: true},
  hour: { type: String, required: true},
  min: { type: String, required: true},
  likedusers: [{ type: String }],
})
exports.Event.index({ author: 1, _id: 1 }).exec()// 按创建时间降序查看用户的活动列表

// 创建每日任务
exports.Task = mongolass.model('Task', {
  name: { type: 'string', default: "Daily Task"},
  target: { type: String, required: true},
  joinedusers: [{ type: String }],
  finishedusers: [{ type: String }],
})
exports.Task.index({ target: 1, _id: 1 }).exec()// 按创建时间降序查看任务列表

