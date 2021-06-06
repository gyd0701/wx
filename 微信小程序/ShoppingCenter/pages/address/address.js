// pages/address/address.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    menuitems: {},
    userid:'' ,
    openid:'',
    add:{},
    userInfo: {},
    hasUserInfo: false,
    length:-1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      openid: wx.getStorageSync('openid')
    })
    this.requestadd()
  
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
    this.requestadd()
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

  },
 /**
   * 获取默认地址
   */
  defaultAddre:function(){
    var isdefault=0;
    wx.chooseAddress({
      success: (res) => {
        wx.request({
          url: app.globalData.baseurl + '/wx/insertadd.do',
          method: "post",
          header: { 'content-type': 'application/x-www-form-urlencoded;charset=utf-8' },
          data: {
              insert: JSON.stringify({
              userid: this.data.openid.data,
              name: res.userName,
              mobile: res.telNumber,
              detail: res.cityName+res.countyName+res.detailInfo,
              isdefault:1,
              remark: "获取默认地址，请检查无误后使用"
            })
          }
        })
      },
      fail: function (err) {
        wx.showModal({
          title: '温馨提示',
          content: '请允许我们读取您的通信地址',
          showCancel: false,
          success: function success(res) {
            if (res.confirm) {
              wx.openSetting({

              })
            }
            }
        })
      }
    })
  },

  /**
   * 新增收货地址
   */
  insertAddre:function(){ 
    if (this.data.length<7){
    wx.navigateTo({
      url: 'insertadd/insertadd?userid=' + this.data.openid.data
    })
    }else{
      wx.showToast({
        title: '您的地址过多，请删除一个！',
        icon: 'none',
        duration:3000

      })
    }
  },
/**
   * 修改
   */
  updatatap:function(event){
    wx.navigateTo({
      url: 'alteradd/alteradd?add=' + JSON.stringify(event.currentTarget.dataset.add)
    })
  },
/**
   * 查询该用户所有收货地址
   */
  requestadd:function(){
    wx.request({
      url: app.globalData.baseurl + '/wx/findalladd.do',
      data: { userid: this.data.openid.data },
      success: res => {
        this.setData({
          menuitems: res.data,
          length:res.data.data.length
        })
      }
    })
  }
})