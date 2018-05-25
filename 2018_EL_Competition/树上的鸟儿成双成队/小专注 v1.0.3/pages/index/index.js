var timer;
Page({
  data: {
    clockSty: true,
    position: "false",
    times: ["10:00", "25:00", "30:00", "45:00", "60:00"],
    value: [1],
    seconds: [600, 1500, 1800, 2700, 3600],
    set_second: 1500,
    set_time: "25:00",
    second: 1500,
    time: "25:00",
  },



  changeClock: function () {
    var that = this
    that.setData({
      clockSty: !that.data.clockSty
    })
  },

  bindChange: function (e) {
    var up = "value[0]"
    const val = e.detail.value
    console.log(e)
    this.setData({
      set_time: this.data.times[val[0]],
      set_second: this.data.seconds[val[0]],
      time: this.data.times[val[0]],
      second: this.data.seconds[val[0]],
      [up]: val[0]
    })
  },

  startBtn: function () {
    this.changeClock();
    this.showMenu();
    var that = this
    wx.onAccelerometerChange(function (res) {
      if (res.z > 0.95) {
        if (!that.data.isRunning) {
          that.setData({
            isRunning: true
          })
          Countdown(that);
        }
      } else {
        that.setData({
          isRunning: false
        })
        clearTimeout(timer);
        if (that.data.second != that.data.set_second) {
          keepPause();
        }
      }
    })
  },

  keepPause: function () {
    setTimeout(function () {
      console.log("你死了")
    }, 10000)
    wx.stopAccelerometer({})
  },

  cancelBtn: function () {
    this.changeClock();
    this.hideModal();
    wx.stopAccelerometer({})
    this.setData({
      time: this.data.set_time,
      second: this.data.set_second
    })
  },

  showMenu: function (e) {
    // 用that取代this，防止不必要的情况发生
    var that = this;
    // 创建一个动画实例
    var animation = wx.createAnimation({
      // 动画持续时间
      duration: 350,
      // 定义动画效果，当前是匀速
      timingFunction: 'linear'
    })
    // 将该变量赋值给当前动画
    that.animation = animation
    // 先在x轴偏移，然后用step()完成一个动画
    animation.translateX(410).step()
    // 用setData改变当前动画
    that.setData({
      // 通过export()方法导出数据
      animationData: animation.export(),
      // 改变view里面的Wx：if
      chooseSize: true
    })
    // 设置setTimeout来改变y轴偏移量，实现有感觉的滑动
    setTimeout(function () {
      animation.translateX(0).step()
      that.setData({
        animationData: animation.export()
      })
    }, 250)
  },
  hideModal: function (e) {
    var that = this;
    var animation = wx.createAnimation({
      duration: 1000,
      timingFunction: 'linear'
    })
    that.animation = animation
    animation.translateX(800).step()
    that.setData({
      animationData: animation.export()

    })
    setTimeout(function () {
      animation.translateX(0).step()
      that.setData({
        animationData: animation.export(),
        chooseSize: false
      })
    }, 500)
  }

})



function Countdown(that) {
  var second = that.data.second
  that.setData({
    time: formatTime(second),

  })
  if (second == 0) {
    that.setData({
      second: that.data.set_second,
      time: that.data.set_time,
    });
    return;
  }
  timer = setTimeout(function () {
    that.setData({
      second: second - 1
    })
    Countdown(that);
  }, 1000);

  function formatTime(seconds) {
    return [
      parseInt(seconds / 60),
      parseInt(seconds % 60),
    ].join(":").replace(/\b(\d)\b/g, "0$1");
  }
}
