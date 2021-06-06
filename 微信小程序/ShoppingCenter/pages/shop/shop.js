// pages/shop/shop.js
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    carts:[],               // 购物车列表
    hasList:false,          // 列表是否有数据
    totalPrice:0,           // 总价，初始为0
    selectAllStatus:false ,   // 全选状态，默认不选
    baseurl:"",
    quantity:0,
  },
selectshoplist:function(){
   //获取缓存中的已添加购物车信息
 var cartItems = wx.getStorageSync('cartItems') || []
 if (cartItems.length!=0) {
   this.setData({
      hasList: true,     
      carts:cartItems
    });
 }
 
},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      baseurl:app.globalData.baseurl
    })
   this.selectshoplist();
    this.getTotalPrice();   
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
    this.selectshoplist();
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
  //计算总价
  getTotalPrice() {
    let carts = this.data.carts;                  // 获取购物车列表
    let total = 0;
    for(let i = 0; i<carts.length; i++) {         // 循环列表得到每个数据
        if(carts[i].selected) {                   // 判断选中才会计算价格
            total += carts[i].quantity * carts[i].price;     // 所有价格加起来
        }
    }
    this.setData({                                // 最后赋值到data中渲染到页面
        carts: carts,
        totalPrice: total.toFixed(2)
    });
},
//选择事件
selectList(e) {
  const index = e.currentTarget.dataset.index;    // 获取data- 传进来的index
  let carts = this.data.carts;                    // 获取购物车列表
  const selected = carts[index].selected;         // 获取当前商品的选中状态
  carts[index].selected = !selected;              // 改变状态
  this.setData({
      carts: carts
  });
  this.getTotalPrice();                           // 重新获取总价
},
//全选事件
selectAll(e) {
    let selectAllStatus = this.data.selectAllStatus;    // 是否全选状态
    selectAllStatus =!selectAllStatus;
    let carts = this.data.carts;

    for (let i = 0; i < carts.length; i++) {
        carts[i].selected = selectAllStatus;            // 改变所有商品状态
    }
    this.setData({
        selectAllStatus: selectAllStatus,
        carts: carts
    });
    this.getTotalPrice();                               // 重新获取总价
},// 增加数量
addCount(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let quantity = carts[index].quantity;
    quantity = quantity + 1;
    carts[index].quantity = quantity;
    this.setData({
      carts: carts
    });
    wx.setStorageSync("cartItems", carts)  //存缓存
    this.getTotalPrice();
},
// 减少数量
minusCount(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let quantity = carts[index].quantity;
    if(quantity <= 1){
      return false;
    }
    quantity = quantity - 1;
    carts[index].quantity = quantity;
    this.setData({
      carts: carts
    });
    wx.setStorageSync("cartItems", carts)  //存缓存
    this.getTotalPrice();
},
//删除商品
deleteList(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let selectAllStatus = this.data.selectAllStatus;    // 是否全选状态
    // wx.showModal({
    //   title: '提示',
    //   content: '您确定要删除该商品吗？',
    //   success(res){
    //     if (res.confirm) {
          carts.splice(index,1);              // 删除购物车列表里这个商品 
        // }
    //   }
    // }) 
    this.setData({
          carts: carts
          });
    wx.setStorageSync("cartItems", carts)  //存缓存
   
   
   if(!carts.length){                  // 如果购物车为空
            this.setData({
              hasList: false ,             // 修改标识为false，显示购物车为空页面
             selectAllStatus:false
            });
          }   this.getTotalPrice();      // 重新计算总价格
},
  goindex:function(){
  wx.switchTab({
    url: '/pages/list/list',
  })
  },
  product: function (event) {
    wx.navigateTo({
      url: '/pages/productDetails/productDetails?p_id=' + event.currentTarget.dataset.productId
    })
  },
  //结算
  buy:function(){
    
    let carts = this.data.carts;                  // 获取购物车列表
    let productlist=[];
    for(let i = 0; i<carts.length; i++) {         // 循环列表得到每个数据
        if(carts[i].selected) {                   // 判断选中
          productlist.push(carts[i]);     
        }
    }
    if (productlist.length!=0) {
       wx.navigateTo({
      url: '/pages/orderform/orderform?productlist='+JSON.stringify(productlist)+'&summoney='+this.data.totalPrice,
    })
    }else{
      wx.showToast({
        title: '您没有选中任何商品哦！',
        icon: 'none',
        duration: 2000
      })
    }
   
  }

})