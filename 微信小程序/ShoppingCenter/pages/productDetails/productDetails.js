// pages/list/productDetails/productDetails.js
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    baseurl:"",
    product_id:0,
    product_img:{},
    pname:"",
    price:0,
    unit:0,
    stock:0,
    random:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      product_id:options.p_id,
      baseurl:app.globalData.baseurl
    })
    wx.request({
      url: app.globalData.baseurl +"/product/findone.do",
      data:{
        product_id:options.p_id
      },
      success:res=>{       
        var arr=[];
        for(var i=0;i<res.data.data.length;i++){
            arr[i]=res.data.data[i].url
        };
        this.setData({
          product_img:arr,
          pname:res.data.data[0].pname,
          price:res.data.data[0].price,
          unit:res.data.data[0].unit,
          stock:res.data.data[0].stock,
          random:Math.floor(Math.random()*100+13)
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
  shouye:function(){
    wx.switchTab({
      url: '/pages/index/index',
    })
  },
  gouwuche:function(){
    wx.switchTab({
      url: '/pages/shop/shop',
    })
  },
//加入购物车
Addshop:function(){
 //将数据添加到缓存
 var that = this
 //获取缓存中的已添加购物车信息
 var cartItems = wx.getStorageSync('cartItems') || []
 //判断购物车缓存中是否已存在该货品
 var exist = cartItems.find(function (ele) {
   return ele.id === that.data.product_id
 })
 if (exist) {
   //如果存在，则增加该货品的购买数量
   exist.quantity = parseInt(exist.quantity) + 1
 } else {
   //如果不存在，传入该货品信息
   cartItems.push({
     id: that.data.product_id,
     quantity:1,
     price: that.data.price,
     pname: that.data.pname,
     product_img: that.data.product_img[0]
   })
 }
 //加入购物车数据，存入缓存
 wx.setStorage({
   key: 'cartItems',
   data: cartItems,
   success: function (res) {
     //添加购物车的消息提示框
     wx.showToast({
       title: "已添入购物车",
       icon: "none",
       durantion: 2000
     })
   }
 })
},
//生成订单
buy:function(){
var productlist=[{id:this.data.product_id,quantity:1,price:this.data.price,pname:this.data.pname,product_img:this.data.product_img[0]}];
wx.navigateTo({
  url: '/pages/orderform/orderform?productlist='+JSON.stringify(productlist)+'&summoney='+this.data.price,
})

}
})