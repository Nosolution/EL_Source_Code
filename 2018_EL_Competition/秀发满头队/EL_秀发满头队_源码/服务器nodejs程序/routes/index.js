module.exports = function (app) {
  app.get('/', function (req, res) {
    res.redirect('/data')
  })
  app.use('/signup', require('./signup'))
  app.use('/signin', require('./signin'))
  app.use('/signout', require('./signout'))
  app.use('/posts', require('./posts'))
  app.use('/comments', require('./comments'))
  app.use('/data', require('./data'))
  app.use('/tasks', require('./tasks'))
  app.use('/users', require('./users'))

  // 404 page
  app.use(function (req, res) {
    if (!res.headersSent) {
      res.status(404).render('404')
    }
  })
}
