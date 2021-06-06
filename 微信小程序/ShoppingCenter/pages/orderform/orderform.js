// pages/orderform/orderform.js
const app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseurl:"",
    productlist:[],
    array: ['到店自提免运费', '本村配送加一元', '快递邮寄十五元'],
    freight:[0,1,15],
    index: 0,
    delivery:"",
    summoney:0,
    money:0,
    add:{},
    order_count:0,
    ordercode:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      productlist:JSON.parse(options.productlist),
      baseurl:app.globalData.baseurl,
      money:options.summoney,
      summoney:options.summoney
    }),

    wx.request({
      url: app.globalData.baseurl + '/wx/defaultadd.do',
      data: { userid: wx.getStorageSync('openid').data },
      success: res => {
        this.setData({
          add: res.data.data,
        })
      }
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

  },
  bindChange: function(e) {
    this.setData({
      index: e.detail.value,
      summoney:parseFloat(this.data.freight[e.detail.value])+parseFloat(this.data.money)
    })
  }, 
  /**
   * 修改收货地址
   */
  updatatap:function(event){
    wx.navigateTo({
      url: '/pages/address/alteradd/alteradd?add=' + JSON.stringify(event.currentTarget.dataset.add)
    })
  },

  //支付
  pay:function(){
    //  let orderdata={};
    //  let user_id="";
    let count=0;
    let productlist=this.data.productlist;
    for(let i = 0; i<productlist.length; i++) {         // 循环列表得到每个数据
      count=count+productlist[i].quantity
    }
      this.setData({
         order_count:count
      })
    wx.request({
        url: app.globalData.baseurl + '/orderform/unorder.do',
        data:{
           orderdata:JSON.stringify({
             user_id:wx.getStorageSync('openid').data,
             freight:this.data.freight[this.data.index],
             product_price:this.data.money,
              total_price:this.data.summoney,
              total_count:this.data.order_count,
              add_id:this.data.add.id,
              productlist:this.data.productlist
           })
        },
        header: { 'content-type': 'application/x-www-form-urlencoded;charset=utf-8' },
        method:'post',
        success:res=>{
            this.setData({
              ordercode:res.data
            })
        }
      })

    wx.requestPayment({
    timeStamp: Date.parse(new Date()),
    nonceStr:this.data.ordercode,//订单号
    package: '',
    signType: 'MD5',
    paySign: '',
    success (res) { 
      console.log("支付成功")
    },
    fail (res) {
      wx.switchTab({
        url: '/pages/user/user',
      })
      console.log("支付失败")
     }
  })
  }
  
})