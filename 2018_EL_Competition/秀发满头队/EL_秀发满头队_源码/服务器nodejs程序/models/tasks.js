const Task = require('../lib/mongo').Task

module.exports = {
  // 创建每日任务
  create: function create (task) {
    return Task
      .create(task)
      .exec()
  },

  // 按创建时间降序获取所有任务
  getTasks: function getTasks () {
    return Task
      .find()
      .sort({ _id: -1 })
      .exec()
  },

  // 重设一项任务
  resetTask: function resetTask (taskId, target) {
    return Task
      .update({ _id: taskId }, { $unset: { joinedusers:[], finishedusers:[] }, $set: { target: target }})
      .exec()
  },

  // 删除任务
  delTaskById: function delTaskById (taskId) {
    return Task.deleteOne({ _id: taskId })
      .exec()
  },

  // 删除所有任务
  delAllTasks: function delAllTasks () {
    return Task.deleteMany().exec()
  },

  // 参加任务
  joinTask: function joinTask (taskId, name) {
    return Task
      .update({ _id: taskId}, { $addToSet: { joinedusers: name }})
      .exec()
  },

  // 完成任务
  finishTask: function finishTask (taskId, name) {
    return Task
      .update({ _id: taskId}, { $pull: { joinedusers: name }, $addToSet: { finishedusers: name}})
      .exec()
  },

  // 退出任务
  exitTask: function exitTask (taskId, name) {
    return Task
      .update({_id: taskId}, { $pull: {joinedusers: name }, $pull: {finishedusers: name}})
      .exec()
  },

  // 根据名称查找任务
  getTaskByName: function getTaskByName (name) {
    return Task
      .findOne({ name: name })
      .exec()
  },
}