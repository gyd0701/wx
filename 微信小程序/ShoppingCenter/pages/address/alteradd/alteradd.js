// pages/address/alteradd/alteradd.js
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    add:{},
    name:"收货人姓名：",
    mobile:"手机号码：",
    detail:"收货地址:",
    alteradddata:{},
    addid:"",
    isdefault:0
  },

  formSubmit:function(e){
    var warn="";
    var that = this;
    var flag=false;
    if (e.detail.value.name == "") {
      warn = "请填写您的真实姓名！";
    } else if (e.detail.value.mobile == "") {
      warn = "请填写您的手机号！";
    } else if (!(/^1(3|4|5|7|8)\d{9}$/.test(e.detail.value.mobile))) {
      warn = "手机号格式不正确";
    } else if (e.detail.value.detail == "") {
      warn = "请输入您的详细收货地址";
    } else {
      flag=true;
    wx.request({
      url: app.globalData.baseurl + '/wx/alteradd.do',
      method:"post",
      header: { 'content-type': 'application/x-www-form-urlencoded;charset=utf-8'},  
      data: {
        alteradddata: JSON.stringify({
          userid: wx.getStorageSync('openid').data,
          name: e.detail.value.name,
          mobile: e.detail.value.mobile,
          detail: e.detail.value.detail,
          isdefault: this.data.isdefault,
          remark: e.detail.value.remark
        }),
          addid:this.data.add.id
              },
      complete: function (res) { 
        wx.reLaunch({
            url: '../address',
          })
      }
    })
    }
    if (flag == false) {
      wx.showToast({
        title: warn,
        icon:'none'
      })
    }
  },
  defaultChange: function (e) {
    if (e.detail.value) {
      this.setData({ isdefault: 1 });
    } else {
      this.setData({ isdefault: 0 });
    }
  },
  deladd:function(){
    wx.request({
      url: app.globalData.baseurl + '/wx/deladd.do',
      data: {
        addid: this.data.add.id
      },
      complete: function (res) {
        
        wx.reLaunch({
          url: '../address',
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      add:JSON.parse(options.add)
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})