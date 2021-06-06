// pages/list/list.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
  category:{},
  baseurl:"",
  productlist:{},
  categoryId:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      baseurl: app.globalData.baseurl
    });
    wx.request({
      url: app.globalData.baseurl +"/category/findall.do",
      success:res=>{
        this.setData({
          category:res.data
        })
        this.productlist()

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
  findproductlist:function(event){
    this.setData({
      categoryId:event.currentTarget.dataset.categoryId
    })
    this.productlist()
  },
  product: function (event) {
    wx.navigateTo({
      url: '/pages/productDetails/productDetails?p_id=' + event.currentTarget.dataset.productId
    })
  },
  productlist:function(){
     //请求商品列表
     wx.request({
      url: app.globalData.baseurl +"/product/findAll.do",
      data:{
          categoryId:this.data.categoryId
      },
      success:res=>{
        console.log(res.data)
        this.setData({
          productlist:res.data
        })
        
      }
    })
  }
})