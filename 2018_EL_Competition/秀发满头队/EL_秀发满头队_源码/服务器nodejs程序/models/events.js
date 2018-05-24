const Event = require('../lib/mongo').Event

module.exports = {
  // create an event
  create: function create (event) {
    return Event
      .create(event)
      .exec()
  },

  // 通过活动 id 获取一项活动
  getEventById: function getEventById (eventId) {
    return Event
      .findOne({ _id: eventId })
      .populate({ path: 'author', model: 'User' })
      .exec()
  },

  // 按创建时间降序获取所有用户活动或者某个特定用户的所有活动
  getEvents: function getEvents (author) {
    const query = {}
    if (author) {
      query.author = author
    }
    return Event
      .find(query)
      .populate({ path: 'author', model: 'User' })
      .addCreatedAt()
      .sort({ _id: -1 })
      .exec()
  },


  // 通过活动 id 获取一项原生活动（编辑活动）
  getRawEventById: function getRawEventById (eventId) {
    return Event
      .findOne({ _id: eventId })
      .populate({ path: 'author', model: 'User' })
      .exec()
  },

  // 通过活动 id 更新一项活动
  updateEventById: function updateEventById (eventId, data) {
    return Event.update({ _id: eventId }, { $set: data }).exec()
  },

  // 通过活动 id 删除一项活动
  delEventById: function delEventById (eventId) {
    return Event.deleteOne({ _id: eventId })
      .exec()
  },

  // 通过活动 id 点赞
  incLike: function incLike (eventId, name) {
    return Event
      .update({ _id: eventId }, { $addToSet: { likedusers: name } })
      .exec()
  },

  // 通过活动 id 取消点赞
  decLike: function decLike (eventId, author) {
    return Event
      .update({ _id: eventId }, { $pull: { likedusers: author } })
      .exec()
  },

  // 根据日期查找活动
  getEventsByDate: function getEventsByDate (author, year, month, day) {
    const query = {}
    query.author = author
    query.year = year
    query.month = month
    query.day = day
    return Event
      .find(query)
      .populate({ path: 'author', model: 'User' })
      .sort({ _id: -1 })
      .exec()
  },

  //删除所有活动
  delAllEvents: function delAllEvents () {
    return Event.deleteMany()
      .exec()
  },
}
