const backgroundAudioManager = wx.getBackgroundAudioManager()
//var status = ['play', 'pause']
var timer;
var onPlay = false;
Page({

  data: {
    status: 'play',
    second: 3600,
    songList: [
      {
        name: "cloud",
        isSelected: "yes",
        url: "http://207.244.97.86/sounds/hipster/highway160.mp3",
        name1:"风声萧萧"
      },
      {
        name: "fire",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/hipster/fireplace.mp3",
        name1:"烈火熊熊"
      },
      {
        name: "bird",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/hipster/birds160.mp3",
        name1: "鸟鸣铮铮"
      },
      {
        name: "sea",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/ocean/pebble-beach.mp3",
        name1: "海浪卷卷"
      },
      {
        name: "rain",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/rain/rainbest160.mp3",
        name1: "春雷滚滚"
      },
      {
        name: "piano",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/hipster/satie160.mp3",
        name1: "琴声绕梁"
      },
      {
        name: "coffee",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/cafe/paris-cafes160.mp3",
        name1: "人声鼎沸"
      },
      {
        name: "bell",
        isSelected: "no",
        url: "http://207.244.97.86/sounds/windchime160.mp3 ",
        name1: "风铃叮当"
      }
    ],
    theSelected: "cloud",
    src: "http://207.244.97.86/sounds/hipster/highway160.mp3",
    srcOfPic: "/images/cloud.jpg",
  },


  iconChange: function (event) {
    if (this.data.theSelected === event.currentTarget.dataset.theSelected) {
      this.play_pause();
    }
    else {
      this.setData({
        theSelected: event.currentTarget.dataset.theSelected,
      })

      for (var i = 0; i < this.data.songList.length; i++) {
        var up = "songList[" + i + "].isSelected"
        if (this.data.songList[i].name != this.data.theSelected) {
          this.setData({
            [up]: "no"
          })
        } else {
          this.setData({
            [up]: "yes",
            src: this.data.songList[i].url,
          })
        }
      }
      this.newSongPlay();
      if (this.data.status === 'play') {
        this.play_pause();
      }
    }
  },

  //播放设置
  newSongPlay: function () {
    console.log("play new");
    backgroundAudioManager.src = this.data.src;
    backgroundAudioManager.play();
  },
  continuePlay: function () {
    console.log("continue");
    backgroundAudioManager.play();
  },
  pause: function () {
    console.log("pause")
    backgroundAudioManager.pause()
  },

  //以下是播放按钮
  play_pause: (function (flag) {
    return function () {
      flag = !flag
      if (flag) {
        if (this.data.time === this.data.set_time) {
          this.newSongPlay();
        } else {
          this.continuePlay();
        }
        this.setData({
          status: 'pause'
        })
        Countdown(this);
      } else {
        this.setData({
          status: 'play'
        })
        this.pause();
        clearTimeout(timer);
      }
    }
  })(0),

 


})//end page

//一下是计时器
function Countdown(that) {
  var second = that.data.second
  that.setData({
    time: formatTime(second),

  })
  if (second == 0) {
    that.setData({
      second: 3600,
      status: 'play',
    });
    backgroundAudioManager.stop()
    return;
  }
  timer = setTimeout(function () {
    that.setData({
      second: second - 1
    })
    Countdown(that);
  }, 1000);
}




