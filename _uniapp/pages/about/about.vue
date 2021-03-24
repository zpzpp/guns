<template>
  <view class="about">
    <tn-custom>
      <block slot="left">
        <view class='action border-custom text-white' :style="'width:' + Custom.width + 'px;height:' + Custom.height + 'px;margin-left:calc(750rpx - ' + Custom.right + 'px)'"
          @click="navHome">
          <text class='cuIcon-back'></text>
          <text class='cuIcon-homefill'></text>
        </view>
      </block>
    </tn-custom>

    <canvas canvas-id="bubble" :style="'width:' + width + 'px;height:' + height + 'px'" class="like-fx"></canvas>
    <like-fx ref="likeFx" :width="width" :height="height"></like-fx>

    <!-- <canvas canvas-id="bubble" :style="'width:' + 375 + 'px;height:' + 1920 + 'px'" class="like-fx"></canvas> -->

    <view class="container about-bg" :style="'margin-top:-' + CustomBar + 'px;background-image:url(' + background_image + ')'">
      <!-- <view class="detail-imgs">
            <block>
                <image src='{{background_image}}' mode='widthFix' class='tn-bg-cart'></image>
            </block>
        </view> -->
    </view>

    <button class='' open-type="contact">
      <image src='/static/images/fenxiang.png' class='share-img png round shadow-lg bg-white' mode='aspectFit'>
      </image>
    </button>
  </view>
</template>

<script>
  import LikeFx from '@/components/likeFx/likeFx.vue'
  import {
    getServerConfigValue
  } from '@/api/server-config';

  export default {
    components: {
      LikeFx
    },
    data() {
      return {
        Custom: this.Custom,
        CustomBar: this.CustomBar,
        background_image: '', // 背景图片
        from: '', // 标记从什么地方跳转到当前页面
        animation_timer: null, // 动画定时器
        width: 375,
        height: 1920
      }
    },
    onLoad(options) {
      this.from = options.from || ''
      this._loadData()
      this._startLikeAnimation()
    },
    onShareAppMessage() {
      return {
        title: '和东东们一起愉快的玩耍叭',
        imageUrl: '/static/images/share.jpg',
        path: '/pages/about/about?from=share'
      }
    },
    onShareTimeline() {
      return {
        title: '关于图鸟',
        query: 'from=share'
      }
    },
    methods: {
      // 跳转回首页
      navHome() {
        clearTimeout(this.animation_timer)

        if (this.from == 'share') {
          uni.navigateTo({
            url: '../index/index'
          })
        } else {
          uni.navigateBack({
            delta: 1
          })
        }
      },
      // 加载数据
      _loadData() {
        getServerConfigValue({
          field: 'mp_about_bg'
        }).then((res) => {
          const {
            data
          } = res
          this.background_image = data.hasOwnProperty('mp_about_bg') ? data.mp_about_bg : ''
        })
      },
      _startLikeAnimation() {
        this.animation_timer = setInterval(() => {
          this.$refs.likeFx.likeClick()
        }, 300)
      }
    }
  }
</script>

<style scoped>
  /* pages/about/about.wxss */
  .about-bg {
    background-size: cover;
    width: 100vw;
    height: 100vh;
    justify-content: center;
    flex-direction: column;
    color: #fff;
  }

  .edit-fixed {
    position: fixed;
    width: 100%;
    bottom: 0;
    z-index: 1024;
    box-shadow: 0 1rpx 6rpx rgba(0, 0, 0, 0.1);
  }

  .detail-imgs image {
    width: 100%;
    float: left;
    /* height:400rpx; 不定高了*/
    border: 0;
    padding: 0;
    margin: 0
  }

  .share-img {
    position: fixed;
    padding: 10rpx;
    width: 100rpx;
    height: 100rpx;
    /* top: 680rpx; */
    bottom: 200rpx;
    right: 20rpx;
    z-index: 1024;
    opacity: 0.8;
    box-shadow: 0rpx 8rpx 30rpx 0rpx rgba(0, 0, 0, 0.3);
    border: none;
  }

  .about {
    margin: 0;
    width: 100%;
    height: 100vh;
    color: #fff;
    background: linear-gradient(-120deg, #F15BB5, #9A5CE5, #01BEFF, #00F5D4);
    /* background: linear-gradient(-120deg, #0976ea, #c471f5, #f956b6, #ea7e0a); */
    background-size: 500% 500%;
    animation: gradientBG 15s ease infinite;
  }

  @keyframes gradientBG {
    0% {
      background-position: 0% 50%;
    }

    50% {
      background-position: 100% 50%;
    }

    100% {
      background-position: 0% 50%;
    }
  }

  .container {
    width: 100%;
    position: absolute;
    text-align: center;
  }

  .like-fx {
    position: fixed;
    right: 0;
    z-index: 1024;
    pointer-events: none;
    /* background-color: red; */
  }
</style>
