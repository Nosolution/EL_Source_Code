module.exports = {
  port: 80,
  session: {
    secret: 'nicehair',
    key: 'nicehair',
    maxAge: 2592000000
  },
  mongodb: 'mongodb://localhost:27017/nicehair'
}
