<template>
  <view class="business-detail">
    <tn-custom bg-color="bg-white shadow">
      <block slot="left">
        <navigator class='action ' open-type="navigateBack" :delta="1" hover-class="none">
          <text class='cuIcon-back_android' style="font-size:48rpx"></text>
          <text class="tn-custom-text">业务 - {{businessData.title}}</text>
        </navigator>
      </block>
    </tn-custom>

    <view class="cu-timeline margin-top-sm content">
      <block v-for="(title_item, title_index) in businessData.business_data" :key="title_index">
        <view class="cu-time">
          <text class='cuIcon-selection text-white text-lg bg-blue round padding-xs'></text>
          <text class='text-xl margin-left'>{{title_item.title}}</text>
        </view>
        <block v-for="(data_item, data_index) in title_item.data" :key="data_index">
          <view class='cu-item text-red'>
            <view class="resume text-lg">
              <text>{{data_item.title}}</text>
              <text class='fr'>{{data_item.sub_title}}</text>
            </view>
            <view class="resume2">
              <text>{{data_item.content}}</text>
            </view>
          </view>
        </block>
      </block>
    </view>


    <view class="cu-bar bg-white tabbar shop shadow edit-fixed">
      <!-- <button class="action text-gray" open-type='contact'>
            <view class='icon-command text-purple'>
            </view>
            反馈
        </button> -->
      <button class="action text-gray text-shadow">
        <view class='cuIcon-community text-gray'>
        </view>
      </button>
      <view class="action text-gray text-shadow">
        <!-- <view class='icon-friendfavor text-pink'>
                <view class='cu-tag badge'>99</view>
            </view> -->
        <text class="text-sm">{{businessData.query_count}}人已咨询</text>
      </view>
      <view class='bg-gradual-red submit margin-left-xl text-xl'>
        <button v-if="checkUserScopeFlag" class='button-no padding-top-xs' open-type="contact" @contact="handleContact">
          <text class='text-white'>业务咨询</text>
        </button>
        <button v-else class='button-no padding-top-xs' @click="advisoryBusiness">
          <text class='text-white'>业务咨询</text>
        </button>
      </view>
    </view>

    <view class='cu-tabbar-height'></view>

    <tn-wx-login :show="showLogin" :tips="loginMsg" @closeLoginModal="closeLoginModal" @getUserInfo="getUserInfo"></tn-wx-login>
  </view>
</template>

<script>
  import {
    getBusinessByID,
    updateBusinessAdvisoryUser
  } from '@/api/business';
  import {
    checkUserScope,
    getUserInfo
  } from '@/utils/user.js';

  const util = require('@/utils/util')

  export default {
    data() {
      return {
        business_id: 0, // 业务id
        businessData: {
          query_count: 0,
          title: ''
        }, // 业务内容
        checkUserScopeFlag: false, //用户是否已经授权
        showLogin: false, //打开登陆框
        loginMsg: { //登录框的显示内容
          title: '该操作需要登陆',
          msg: '该操作将获取以下授权为了更好的服务：'
        },
      }
    },
    onLoad(options) {
      this.business_id = options.id; // 获取业务的id

      this._loadData();

      this._checkUserScope().then(() => {}).catch(() => {});
    },
    onShareAppMessage() {
      return {
        title: '图鸟业务',
        imageUrl: '/static/images/share.jpg',
      }
    },
    onShareTimeline() {
      return {
        title: '图鸟业务',
        imageUrl: '/static/images/share.jpg',
      }
    },
    methods: {
      advisoryBusiness() {
        // console.log('advisBusiness');
        this._checkUserScope().then(() => {}).catch(() => {
          this.showLogin = true
        });
      },

      // 处理客服消息
      handleContact() {
        this._checkUserScope().then(() => {
          updateBusinessAdvisoryUser({
            id: this.business_id
          }).then((res) => {
            if (res.errorCode === 0) {
              this.businessData.query_count++;

            }
          })
        }).catch(() => {

          this.showLogin = true

        })
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

      // 加载默认数据数据
      _loadData(callback) {
        getBusinessByID({
          id: this.business_id
        }).then((res) => {

          this.businessData = res.data

        })
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
  .cu-timeline .cu-time {
    width: 100%;
    text-align: left;
    padding: 20rpx 0 20rpx 37rpx;
    font-size: 26rpx;
    color: #888;
    display: block;
  }

  .text-red,
  .line-red,
  .lines-red {
    color: #FF3434;
  }

  .margin-avatar {
    margin-left: -15rpx;
  }

  .margin-avatar-bottom {
    margin-bottom: 150rpx;
  }

  .line-blue-tuniao::after {
    border-color: #0070ff !important;
    color: #0070ff;
  }

  .resume {
    /* background: #f1f1f1; */
    padding-top: 10rpx;
    border-radius: 6rpx;
    display: block;
    color: #666;
    line-height: 1.6;
  }

  .resume+.resume {
    margin-top: 20rpx;
  }

  .resume2 {
    padding-top: 10rpx;
    border-radius: 6rpx;
    display: block;
    color: #666;
    line-height: 1.6;
  }

  .edit {
    position: fixed;
    width: 100rpx;
    height: 100rpx;
    bottom: 250rpx;
    right: 30rpx;
    z-index: 1;
    opacity: 0.8;
    border: 1px solid #189eff;
    border-radius: 100rpx;
    box-shadow: 0rpx 0rpx 6rpx rgba(24, 158, 255, 1);
    padding: 20rpx;
  }

  .love {
    position: fixed;
    width: 100rpx;
    height: 100rpx;
    bottom: 550rpx;
    right: 30rpx;
    z-index: 1024;
    opacity: 0.8;
    border: 1px solid #189eff;
    border-radius: 100rpx;
    box-shadow: 0rpx 0rpx 6rpx rgba(24, 158, 255, 1);
    padding: 20rpx;
  }

  .bg-img-cont {
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    height: 350rpx;
  }

  .share-png {
    width: 100rpx;
    height: 100rpx;
    margin: 0 auto;
  }

  .share-wechat {
    width: 35rpx;
    height: 35rpx;
    margin: 0 10rpx -4rpx 0;
    opacity: 0.5;
  }

  .button-no::after {
    border: none;
  }

  .title-pyq {
    background-image: -webkit-linear-gradient(0deg, #1b6cff, #1ca5ff);
    /*1B6CFF 1B42FF*/
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    opacity: 0.5;
  }

  .edit-fixed {
    position: fixed;
    width: 100%;
    bottom: 0;
    z-index: 1024;
    box-shadow: 0 1rpx 6rpx rgba(0, 0, 0, 0.1);
  }

  .button-no {
    border: none;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
  }
</style>
