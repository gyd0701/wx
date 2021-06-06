//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    img: [
      '/img/lunbo/kaiye.jpg',
      '/img/lunbo/618.jpg',
      '/img/lunbo/kaixueji.jpg',
      '/img/lunbo/yuandan.jpg'
    ],
    baseurl:"",
    categorylist:{},
    mrtjplist:{},
    countDownHour: 0, //倒计时 -时
    countDownMinute: 0, //倒计时 -分
    countDownSecond: 0, //倒计时-秒

  },
 
  onLoad: function (options) {
    this.setData({
      baseurl:app.globalData.baseurl
    })
     //设置倒计时时间，1s变换一次
      var interval = setInterval(function () {
      var d = new Date();  //获取系统日期和时间
      var nowHour = d.getHours(); //小时
      var nowMinutes = d.getMinutes(); //分
      var nowSeconds = d.getSeconds(); //秒

      // 显示在倒计时中的小时位
      var hour = 24 - nowHour;

      // 显示在倒计时中的分钟位
      var minutes = 60 - nowMinutes;

      // 显示在倒计时中的秒数
      var seconds = 60 - nowSeconds;
    
      //当小时位、分钟位、秒位小于10时，用字符串拼接的方式显示，例如：06:08:02

      if (hour < 10) {
        hour = "0" + hour;
      }
      if (minutes < 10) {
        minutes = "0" + minutes;
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      this.setData({
        countDownHour: hour,
        countDownMinute: minutes,
        countDownSecond: seconds,
      });
   }.bind(this), 1000);


    wx.request({
      url: app.globalData.baseurl +"/category/findall.do",
      success:res=>{
        this.setData({
          categorylist:res.data
        })
        wx.request({
          url: app.globalData.baseurl +"/product/findmrtjlist.do",
          success:res=>{
            this.setData({
              mrtjplist:res.data
            })
            
          }
        })
      }
    })

  },
 //事件处理函数
 product: function (event) {
  wx.navigateTo({
    url: '/pages/productDetails/productDetails?p_id=' + event.currentTarget.dataset.productId
  })
},
category: function (event) {
  wx.switchTab({
    url: '/pages/list/list'
  })
},
// 搜索
searchInput:function(){
  var that = this
  wx.navigateTo({
    url: '/pages/index/search/search'
  })
},
})
