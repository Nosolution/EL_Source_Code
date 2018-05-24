const User = require('../lib/mongo').User

module.exports = {

  // 创建用户
  create: function create (user) {
    return User.create(user).exec()
  },

  // 注销用户
  remove: function remove (id) {
    return User
      .deleteOne({_id: id})
      .exec()
  },

  // 通过用户名获取用户
  getUserByName: function getUserByName (name) {
    return User
      .findOne({ name: name })
      .addCreatedAt()
      .exec()
  },

  // 通过 id 获取用户
  getUserById: function getUserById (id) {
    return User
      .findOne({ _id: id })
      .exec()
  },

  // 获取所有用户
  getUsers: function getUsers () {
    return User
      .find()
      .exec()
  },

  // 根据番茄数排序
  rankByTomatoes: function rankByTomatoes () {
    return User
      .find()
      .sort({ tomato: -1})
      .exec()
  },

  // 根据得到的赞数排序
  rankByLikes: function rankByLikes () {
    return User
      .find()
      .sort({ like: -1})
      .exec()
  },

  // 更新点赞数
  updateLike: function updateLike (user, like) {
    return User
      .update({ _id: user._id }, { $set: { like: like }})
      .exec()
  },

  // 更改头像
  updateAvatar: function updateAvatar (user, avatar) {
    return User
      .update({_id: user._id }, { $set: { avatar: avatar}})
      .exec()
  },

  // 通过用户 id 更新番茄数
  updateTomato: function updateTomato (id, tomatoNum) {
    return User
      .update({ _id: id }, { $set: { tomato: tomatoNum } })
  },

  // 更新位置
  updatelocation: function updatelocation (id, location) {
    return User
      .update({ _id: id }, { $set: { location: location }})
      .exec()
  },

  // 参加一项任务
  joinTask: function joinTask (id) {
    return User
      .update({_id: id }, {$inc: { credit: -10 }})
      .exec()
  },

  // 完成一项任务
  finishTask: function finishTask (id) {
    return User
      .update({_id: id }, {$inc: { credit: 15 }})
      .exec()
  },

  //删除所有用户
  delAllUsers: function delAllUsers () {
    return User
      .deleteMany()
      .exec()
  },
}
