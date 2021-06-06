const app=getApp();
Page({
 
  /**
   * 页面的初始数据
   */
  data: {
    currtab: 0,
    swipertab: [{ name: '已完成', index: 0 }, { name: '待付款', index: 1 }, { name: '待发货', index: 2 }, { name: '待收货', index: 3 }],
    baseurl:"",
    orderid:""
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.setData({
       baseurl:app.globalData.baseurl,
    })

 
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    // 页面渲染完成
    this.getDeviceInfo()
    this.orderShow()
  },
 
  getDeviceInfo: function () {
    let that = this
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          deviceW: res.windowWidth,
          deviceH: res.windowHeight
        })
      }
    })
  },
 
  /**
  * @Explain：选项卡点击切换
  */
  tabSwitch: function (e) {
    var that = this
    if (this.data.currtab === e.target.dataset.current) {
      return false
    } else {
      that.setData({
        currtab: e.target.dataset.current
      })
    }
  },
 
  tabChange: function (e) {
    this.setData({ currtab: e.detail.current })
    this.orderShow()
  },
 
  orderShow: function () {
    let that = this
    switch (this.data.currtab) {
      case 0:
        that.alreadyShow()
        break
      case 1:
        that.waitPayShow()
        break
      case 2:
        that.waitConsignmentShow()
        break
      case 3:
          that.lostShow()
          break
    }
  },
  alreadyShow: function(){
    wx.request({
      url: this.data.baseurl+'/orderform/findWaitPay.do',
      data:{
        userid: wx.getStorageSync('openid').data,
        status:30
      },
      success:res=>{
         this.setData({
        waitPayOrder: res.data.data,
    })
      }
    })
  },
 
  waitPayShow:function(){
    wx.request({
      url: this.data.baseurl+'/orderform/findWaitPay.do',
      data:{
        userid: wx.getStorageSync('openid').data,
        status:10
      },
      success:res=>{
         this.setData({
        waitPayOrder: res.data.data,
    })
      }
    })
   
  },
  waitConsignmentShow:function(){
    wx.request({
      url: this.data.baseurl+'/orderform/findWaitPay.do',
      data:{
        userid: wx.getStorageSync('openid').data,
        status:20
      },
      success:res=>{
         this.setData({
        waitPayOrder: res.data.data,
    })
      }
    })
  },
 
  lostShow: function () {
    wx.request({
      url: this.data.baseurl+'/orderform/findWaitPay.do',
      data:{
        userid: wx.getStorageSync('openid').data,
        status:40
      },
      success:res=>{
         this.setData({
        waitPayOrder: res.data.data,
    })
      }
    })
  },
  gopay:function(){
    wx.showToast({
      title: "非常抱歉，付款失败！",
      icon: "none",
      durantion: 2000
    })
  },


  Confirm:function(event){
  let that=this;
    wx.showModal({
      title:'提示',
      content: '您确定要收货吗？',
      success: (res)=>{
        if (res.confirm) {
          wx.request({
            url: this.data.baseurl+'/orderform/upstate.do',
            data:{
              orderid:event.currentTarget.dataset.orderId ,
              status:30
            },
            success: ()=>{
              that.lostShow()
            }
          })

        } else if (res.cancel) {
          console.log('用户点击了取消')
        }
      }
    })
  }
})