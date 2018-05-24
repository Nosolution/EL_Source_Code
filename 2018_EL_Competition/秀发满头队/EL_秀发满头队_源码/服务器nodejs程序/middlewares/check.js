module.exports = {
  checkLogin: function checkLogin (req, res, next) {
    if (!req.session.user) {
      return res.send({ status: 'error', error: 'notLogin' })
    }
    next()
  },

  checkNotLogin: function checkNotLogin (req, res, next) {
    if (req.session.user) {
      return res.send({ status: 'error', error: 'login' })
    }
    next()
  }
}
