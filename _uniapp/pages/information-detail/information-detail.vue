<template>
  <view class="information-detail">
    <tn-custom bg-color="TN_navbg shadow">
      <block slot="left">
        <view class='action' @click="goBack">
          <text :class="from === 'share' ? 'cuIcon-homefill' : 'cuIcon-back_android'" style="font-size:48rpx"></text>
          <text class="">资讯 - {{informationData.category.title}}</text>
        </view>
      </block>
    </tn-custom>

    <view class="">

      <view class="title-header">
        <view class="text-black padding-xl text-center">
          {{informationData.title}}
        </view>
      </view>

      <view class="margin text-lg padding-bottom-xl">
        <u-parse :content="informationData.table_data.content" :imageProp="{lazyLoad: true}" @navigate="parseNavigate" />
      </view>

      <view class="cu-bar bg-white solid-bottom padding-left-xs strip-bottom padding-bottom">
        <view class='action'>
          <text class='cuIcon-attentionfavor padding-right-xs'></text>
          <text class="text-df">{{informationData.view_count}}</text>
        </view>
        <view class='action'>
          <button class='cu-btn bg-pink shadow round sm' open-type="share">
            <text class='text-sm padding-right-xs'>分享</text>
            <text class="cuIcon-share text-df"></text>
          </button>

        </view>
      </view>


      <view class="cu-bar bg-white solid-bottom">
        <view class='action'>
          热门推荐
        </view>
      </view>

      <view class="cu-card article no-card padding-top">
        <view class="cu-item" v-for="(recomm_item, recomm_index) in informationData.recomm_data" :key="recomm_index">
          <view class="content" @click="navDetail(recomm_item.id)">

            <view class="desc" style="margin-top:-8rpx">
              <view class='text-content product-title'>
                <view class='text-black name-title-b text-xl'>{{recomm_item.title}}</view>

                <view class='text-gray name-title-a text-lg padding-top-xs'>{{recomm_item.table_data.desc}}</view>
              </view>

              <view class="cu-list  price price-product text-title text-lg text-red" style="margin-top:-8rpx">
                <view class='flex justify-between'>
                  <view :class="'cu-tag bg-label' + (recomm_index%11+1) + ' light round margin-right-sm margin-bottom text-df padding text-bold'">{{recomm_item.category.title}}</view>
                  <view class="margin-top-xs text-gray opacity-a margin-right">
                    <text class="cuIcon-attentionfill"></text> {{recomm_item.view_count}}
                    <text class="cuIcon-appreciatefill margin-left-sm"></text> {{recomm_item.like_count}}
                  </view>
                </view>
              </view>


            </view>
            <image class="product-border solid" style="margin-right:0rpx" :src="recomm_item.main_image.prefix" mode="aspectFill"></image>
          </view>
        </view>
      </view>

      <view class='cu-tabbar-height'></view>

      <view class="cu-list menu shadow edit-fixed bg-white">
        <view class="cu-item">
          <view class='action'>
            <view class="cu-avatar-group margin-left-xs" style="margin:0 0 0 -15rpx">
              <block v-for="(like_item, like_index) in informationData.contentLikeUser" :key="like_index">
                <view class="cu-avatar round margin-xs shadow-sm" :style="'background-image:url(' + like_item.avatar_url + ');'"></view>
              </block>

            </view>
          </view>
          <view class='action' @click="likeTap">
            <text :class="['cuIcon-appreciatefill', isLike ? 'text-pink' : 'text-gray', 'text-xl']"></text>
            <text :class="['padding-left-sm', isLike ? 'text-pink' : 'text-gray', 'text-lg']">{{informationData.like_count}}</text>
          </view>
        </view>
      </view>

    </view>

    <tn-wx-login :show="showLogin" :tips="loginMsg" @closeLoginModal="closeLoginModal" @getUserInfo="getUserInfo"></tn-wx-login>
  </view>
</template>

<script>
  import uParse from '@/components/feng-parse/parse'
  import {
    getInformationByID,
    updateInformationViewUser,
    updateInformationLikeUser,
    updateInformationShareUser
  } from '../../api/information';
  import {
    checkUserScope,
    getUserInfo
  } from '../../utils/user.js';

  const util = require('@/utils/util')

  export default {
    components: {
      uParse
    },
    data() {
      return {
        from: '', // 标记从哪里跳转到该页面
        information_id: 0, // 资讯id
        informationData: {
          title: '',
          category: {
            title: ''
          },
          table_data: {
            content: ''
          },
          view_count: 0,
          recomm_data: []
        }, // 资讯内容
        isLike: false, // 标记是否已经点赞了
        checkUserScopeFlag: false, //用户是否已经授权
        showLogin: false, //打开登陆框
        loginMsg: { //登录框的显示内容
          title: '该操作需要登陆',
          msg: '该操作将获取以下授权:'
        },
      }
    },
    onLoad(options) {
      this.information_id = options.id
      this.from = options.from || ''
      this._loadData();
    },
    onShareAppMessage() {

      this._updateShareData();

      return {
        title: this.informationData.title,
        imageUrl: this.informationData.main_image.prefix,
        path: '/pages/information-detail/information-detail?from=share&id=' + this.information_id,
      };
    },
    onShareTimeline() {

      this._updateShareData();

      return {
        title: this.informationData.title,
        imageUrl: this.informationData.main_image.prefix,
        query: 'from=share&id=' + this.information_id,
      };
    },
    methods: {
      // 点击返回事件
      goBack() {
        if (this.from == 'share') {
          uni.navigateTo({
            url: '/pages/index/index',
          });
        } else {
          uni.navigateBack({
            delta: 1
          });
        }
      },

      // 点击推荐跳转到对应的详情页面
      navDetail(id) {
        wx.navigateTo({
          url: '/pages/information-detail/information-detail?id=' + id,
        })
      },

      // 处理点赞事件
      likeTap() {
        if (this.isLike == true) {
          return;
        }
        this._checkUserScope().then(() => {
          this._updateLike(true, false, true);
        }).catch(() => {
          this.showLogin = true
        });
      },

      //获取用户登陆状态
      getUserInfo() {
        getUserInfo().then(() => {
          this.checkUserScopeFlag = true;
          util.showTips('登陆成功', '请继续执行上一步操作');
          this.closeLoginModal()
        }).catch(() => {
          util.showTips('授权提示', '获取授权失败');
          this.closeLoginModal();
        })
      },

      // 关闭登录窗口
      closeLoginModal() {
        this.showLogin = false
      },

      // 处理富文本的链接点击
      parseNavigate(href) {
        uni.setClipboardData({
          data: href,
          complete: () => {
            uni.showToast({
              title: '复制链接成功'
            })
          }
        })
      },

      // 加载数据
      _loadData(callback) {
        getInformationByID({
          id: this.information_id
        }).then((res) => {
          this.informationData = res.data
          this._checkUserScope().then(() => {
            this._updateView(true);
            this._updateLike(true, true, false);
          }).catch(() => {
            this._updateView(false);
          });
        });
      },

      // 更新内容的分享次数
      _updateShareData() {
        this._checkUserScope().then(() => {
          this._updateShare(true);
        }).catch(() => {
          this._updateShare(false);
        });
      },

      /**
       * 更新查看数据
       */
      _updateView(add_user) {
        return new Promise((resolve, reject) => {
          updateInformationViewUser({
            id: this.information_id,
            add_user: add_user
          }).then((res) => {
            if (res.data) {
              this.informationData.view_count = res.data;
            }
            resolve(res.data);
          }).catch(() => {
            resolve();
          });
        });
      },

      /**
       * 更新点赞数据
       */
      _updateLike(add_user, only_check, get_user_list) {
        return new Promise((resolve, reject) => {
          updateInformationLikeUser({
            id: this.information_id,
            add_user: add_user,
            only_check: only_check,
            get_user_list: get_user_list
          }).then((res) => {
            const {
              data
            } = res;
            if (!only_check && data) {
              this.informationData.like_count = data.like_count;
              this.informationData.contentLikeUser = data.contentLikeUser;
              this.isLike = true
            }
            resolve(res.data);
          }).catch((res) => {
            if (res.errorCode == 40104) {
              this.isLike = true
              return;
            }
            resolve();
          });
        });
      },

      /**
       * 更新分享数据
       */
      _updateShare(add_user) {
        return new Promise((resolve, reject) => {
          updateInformationShareUser({
            id: this.information_id,
            add_user: add_user
          }).then((res) => {
            resolve(res.data);
          }).catch(() => {
            resolve();
          });
        });
      },

      /**
       * 检查当前用户的权限
       */
      _checkUserScope() {
        return new Promise((resolve, reject) => {
          checkUserScope().then((res) => {
            this.checkUserScopeFlag = res
            resolve();
          }).catch((res) => {
            this.checkUserScopeFlag = res
            reject();
          })
        })
      }
    }
  }
</script>

<style scoped>
  .title-header {
    display: flex;
    /* height: 120rpx; */
    font-size: 40rpx;
    align-items: center;
    justify-content: center;
    /* padding: 100rpx 0 60rpx 0; */
    font-weight: bold;
    /* background-size: cover; */
  }

  .cu-bar .content image {
    height: 60rpx;
    width: 240rpx;
  }

  .cardTitle {
    color: #fff;
    padding: 160rpx 60rpx;
    font-size: 40rpx;
    font-weight: 300;
    transform: skew(-10deg, 0deg);
    position: relative;
    text-shadow: 0px 0px 6rpx rgba(0, 0, 0, 0.3)
  }

  /* .cardTitle::before{
  content: "";
  position: absolute;
  width: 60rpx;
  height: 6rpx;
  border-radius: 20rpx;
  background-color: #fff;
  display: block;
  top: 60rpx;
  left: 50rpx;  
  transform: skew(10deg, 0deg);
} */
  /* .cardTitle::after{
  content: "";
  position: absolute;
  width: 140rpx;
  border-radius: 6rpx;
  height: 24rpx;
  background-color: #fff;
  display: block;
  bottom: 76rpx;
  left: 90rpx;  
  transform: skew(10deg, 0deg);
  opacity: 0.1;
} */


  .edit-fixed {
    position: fixed;
    width: 100%;
    bottom: 0;
    z-index: 1024;
    box-shadow: 0 1rpx 10rpx rgba(0, 0, 0, 0.2);
  }
</style>
