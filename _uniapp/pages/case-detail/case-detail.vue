<template>
  <view class="case-detail">
    <tn-custom bg-color="TN_navbg shadow">
      <block slot="left">
        <view class='action' @click="goBack">
          <text :class="from === 'share' ? 'cuIcon-homefill' : 'cuIcon-back_android'" style="font-size:48rpx"></text>
          <text class="">案例 - {{caseData.category.title}}</text>
        </view>
      </block>
    </tn-custom>

    <view>
      <view class="title-header">
        <view class="text-black padding-xl  text-center">
          {{caseData.title}}
        </view>
      </view>

      <view class="margin text-lg">
        <u-parse :content="caseData.table_data.content" :imageProp="{lazyLoad: true}" @navigate="parseNavigate" />
      </view>

      <view class="cu-tabbar-height"></view>

      <view class="cu-bar bg-white tabbar shop shadow edit-fixed">
        <button class="action text-gray text-shadow">
          <view class='cuIcon-command text-gray'>
          </view>
        </button>
        <view class="action text-gray text-shadow">
          <text class="text-sm">{{caseData.like_count}}人已咨询</text>
        </view>
        <view class='bg-gradual-red submit margin-left-xl text-xl'>
          <button v-if="checkUserScopeFlag" class='button-no' open-type="contact" @contact="handleContact">
            <text class='text-white'>业务咨询</text>
          </button>
          <button v-else class='button-no' @click="advisoryBusiness">
            <text class='text-white'>业务咨询</text>
          </button>
        </view>
      </view>
    </view>

    <tn-wx-login :show="showLogin" :tips="loginMsg" @closeLoginModal="closeLoginModal" @getUserInfo="getUserInfo"></tn-wx-login>
  </view>
</template>

<script>
  import uParse from '@/components/feng-parse/parse'
  import {
    getCaseByID,
    updateCaseViewUser,
    updateCaseLikeUser,
    updateCaseShareUser
  } from '@/api/case'
  import {
    checkUserScope,
    getUserInfo
  } from '@/utils/user.js'

  const util = require('@/utils/util')

  export default {
    components: {
      uParse
    },
    data() {
      return {
        from: '', // 标记从哪里跳转到该页面
        case_id: 0, // 案例id
        caseData: {
          category: {
            title: ''
          },
          title: '',
          like_count: 0,
          table_data: {
            content: ''
          }
        }, // 案例内容
        isAdvisory: false, // 标记是否已经咨询过
        checkUserScopeFlag: false, //用户是否已经授权
        showLogin: false, //打开登陆框
        loginMsg: { //登录框的显示内容
          title: '该操作需要登陆',
          msg: '该操作将获取以下授权为了更好的服务：'
        }
      }
    },
    onLoad(options) {
      this.case_id = options.id
      this.from = options.from || ''

      this._loadData();
    },
    onShareAppMessage() {
      this._updateShareData();

      return {
        title: this.caseData.title,
        imageUrl: this.caseData.main_image.prefix,
        path: '/pages/case-detail/case-detail?from=share&id=' + this.case_id,
      };
    },
    onShareTimeline() {
      this._updateShareData();

      return {
        title: this.caseData.title,
        imageUrl: this.caseData.main_image.prefix,
        query: 'from=share&id=' + this.case_id,
      };
    },
    methods: {
      // 返回事件
      goBack() {
        if (this.from == 'share') {
          uni.navigateTo({
            url: '/pages/index/index'
          })
        } else {
          uni.navigateBack({
            delta: 1
          })
        }
      },

      // 点击业务咨询按钮事件
      advisoryBusiness() {
        this._checkUserScope().then(() => {}).catch(() => {
          this.showLogin = true
        });
      },

      // 处理客服消息
      handleContact() {
        if (!this.isAdvisory) {
          this._checkUserScope().then(() => {
            this._updateLike(true, false).then((res) => {
              if (res) {
                this.caseData.like_count = res;

                this.isAdvisory = true;
              }
            }).catch(() => {});
          }).catch(() => {
            this.showLogin = true
          });
        }

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
        getCaseByID({
          id: this.case_id
        }).then((res) => {
          this.caseData = res.data
          this._updateViewData();
          this._updateLike(false, true);
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
       * 更新内容的查看次数
       * params:
       * add_user 标记是否添加用户信息
       */
      _updateViewData() {
        this._checkUserScope().then(() => {
          this._updateView(true);
        }).catch(() => {
          this._updateView(false);
        })
      },

      /**
       * 更新查看数据
       */
      _updateView(add_user) {
        return new Promise((resolve, reject) => {
          updateCaseViewUser({
            id: this.case_id,
            add_user: add_user
          }).then((res) => {
            resolve(res.data);
          }).catch(() => {
            resolve();
          });
        });
      },

      /**
       * 更新点赞数据
       */
      _updateLike(add_user, only_check) {
        return new Promise((resolve, reject) => {
          updateCaseLikeUser({
            id: this.case_id,
            add_user: add_user,
            only_check: only_check,
          }).then((res) => {
            resolve(res.data);
          }).catch((res) => {
            if (res.errorCode == 40104) {
              this.isAdvisory = true;
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
          updateCaseShareUser({
            id: this.case_id,
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
          });
        });
      },
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

  .button-no {
    border: none;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
  }

  .edit-fixed {
    position: fixed;
    width: 100%;
    bottom: 0;
    z-index: 1024;
    box-shadow: 0 1rpx 6rpx rgba(0, 0, 0, 0.1);
  }
</style>
