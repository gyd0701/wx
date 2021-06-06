const app=getApp();
Page({

data: {
  inputValue: '',//输入的值
  getSearch: [],//历史记录
  modalHidden: true,
  name_focus: true,//获取焦点
  keydown_number: 0,//检测input框内是否有内容
  store:'',
  isplistnull:true,
  searchllist:{},
  baseurl:""
},

/**
 * 生命周期函数--监听页面加载
 */
onLoad: function (options) {
  //获取上一页面传递过来的值
  this.setData({
    baseurl: app.globalData.baseurl
  });
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
  var getSearch = wx.getStorageSync('searchData');
  var store = this.data.store
  //只显示十条历史记录
  if (getSearch.length < 10) {
    getSearch.push(this.data.inputValue)
  } else {
    getSearch.splice(0, 1)
    getSearch.push(this.data.inputValue)
  }
  this.setData({
    getSearch: getSearch,
    inputValue: store
  })
  console.log('search is onshow')
},

/**
 * 生命周期函数--监听页面隐藏
 */
onHide: function () {
  console.log('search is onHide')
  wx.redirectTo({
    url: '../search/search'
  })
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
//获取输入的值
searchInput: function (e) {
  this.setData({
    inputValue: e.detail.value
  })
  console.log('bindInput'+this.data.inputValue)  
  }, 
//点击赋值到input框
this_value: function (e) {
  this.setData({
    name_focus: true
  })
  let value = e.currentTarget.dataset.text;
  this.setData({
    inputValue: value,
    keydown_number: 1
  })
},
//回车搜索
setSearchStorage:function(e){
  let store = e.currentTarget.dataset.store
  let data;
  let localStorageValue = [];
  //设置一个空数组,把每次输入的值存进去
  //判断如果小于等于10就添加进数组,否则就删除下标为零的值
    var searchData = wx.getStorageSync('searchData') || []
  if (searchData.length <= 10){
    searchData.push(this.data.inputValue)
  }else{
    searchData.splice(0, 1)
    searchData.push(this.data.inputValue)
  }
    wx.setStorageSync('searchData', searchData)
    let pages = getCurrentPages();//当前页面
    let prevPage = pages[pages.length - 2];//上一页面
      //把值传入上一搜索主页面
    prevPage.setData({
      store: e.currentTarget.dataset.store,
    });
   
    wx.request({
      url: app.globalData.baseurl +"/product/searchproduct.do",
      data:{
        store:e.currentTarget.dataset.store
      },
          success:res=>{
            if(res.data.data.length==0){
           wx.showToast({
            title: '没有查找到您搜索的商品哦！',
            icon: 'none',
            duration: 2000
          })
          }else{
            this.setData({
              searchllist:res.data.data,
              isplistnull:false,
            })
          }
          }
    })
},
modalChangeConfirm: function () {
  wx.setStorageSync('searchData', [])
  this.setData({
    modalHidden: true
  })
  wx.redirectTo({
    url: '../search/search'
  })
},
modalChangeCancel: function () {
  this.setData({
    modalHidden: true
  })
},
clearSearchStorage: function () {
  this.setData({
    modalHidden: false
  })
},
product: function (event) {
  wx.navigateTo({
    url: '/pages/productDetails/productDetails?p_id=' + event.currentTarget.dataset.productId
  })
},
})