<template>
  <view class="components-theme">
    <tn-custom>
      <block slot="left">
        <!-- #ifdef MP-WEIXIN -->
        <navigator class='action border-custom text-white' open-type="navigateBack" :delta="1" hover-class="none"
          :style="'width:' + Custom.width + 'px;height:' + Custom.height + 'px;margin-left:calc(750rpx - ' + Custom.right + 'px)'">
          <text class='cuIcon-back'></text>
          <text class='cuIcon-homefill'></text>
        </navigator>
        <!-- #endif -->
        <!-- #ifdef H5 -->
        <navigator class='action border-custom text-white' open-type="navigateBack" :delta="1" hover-class="none" style="width: 174rpx;height: 64rpx;margin-left: 20rpx">
          <text class='cuIcon-back'></text>
          <text class='cuIcon-homefill'></text>
        </navigator>
        <!-- #endif -->
      </block>
    </tn-custom>

    <view class='UCenter-bg' :style="'background-image: url(' + pic[topBackGroupImageIndex].link + ');margin-top:-' + CustomBar + 'px;'">
      <block>
        <view class='text-center'>
          <view class="cu-avatar round margin-right-sm shadow-blur bg-img" style="background-image:url(https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1568100394340-assets/web-upload/792c999f-bf08-41d5-a33b-21fc5bb263a2.jpeg);">
          </view>
          <view class="padding text-blue text-xl">可我会像</view>
        </view>
      </block>

      <image src='https://cdn.nlark.com/yuque/0/2019/gif/280373/1570670848649-assets/web-upload/3dbaa72a-062b-470f-9b9d-058ff8f85ab8.gif'
        mode='scaleToFill' class='gif-wave'></image>

    </view>

    <block>

      <view class='padding flex text-center text-grey bg-white shadow-warp-my'>
        <view class='flex flex-sub flex-direction solid-right'>
          <view class="text-xxl text-orange">829</view>
          <view class="margin-top-sm">
            <text class='cuIcon-hot'></text> 人气</view>
        </view>
        <view class='flex flex-sub flex-direction solid-right'>
          <view class="text-xxl text-blue">86</view>
          <view class="margin-top-sm">
            <text class='cuIcon-share'></text> 分享</view>
        </view>
        <view class='flex flex-sub flex-direction'>
          <view class="text-xxl text-red">142</view>
          <view class="margin-top-sm">
            <text class='cuIcon-like'></text> 爱心</view>
        </view>
      </view>


      <view class="cu-list menu card-menu margin-top-xl margin-bottom-xl shadow-shop bg-white text-black my-radius sm-border">
        <view class="cu-item ">
          <view class='content'>
            <image src='/static/images/shoucang.png' class='png' mode='aspectFit'></image>
            <text class='text-lg margin-sm'>切换主题</text>
          </view>
          <view class='action'>
            <radio-group>
              <view class="cu-form-group">
                <radio class='green radio' @click="switchImage(0)"></radio>
                <radio class='blue radio margin-left-sm' checked @click="switchImage(1)"></radio>
                <radio class='orange radio margin-left-sm' @click="switchImage(2)"></radio>
                <radio class='cyan radio margin-left-sm' @click="switchImage(3)"></radio>
              </view>
            </radio-group>
          </view>
        </view>

        <view class="cu-item " bindtap="">
          <button class='content cu-btn'>
            <image src='/static/images/dingdan.png' class='png' mode='aspectFit'></image>
            <text class='text-lg margin-sm'>访客记录</text>
          </button>
        </view>

        <view class="cu-item ">
          <button class='content cu-btn' open-type="contact">
            <image src='/static/images/kefu.png' class='png' mode='aspectFit'></image>
            <text class='text-lg margin-sm'>合作勾搭</text>
          </button>
        </view>
        <view class="cu-item">
          <button class='content cu-btn' open-type="feedback">
            <image src='/static/images/fankui.png' class='png' mode='aspectFit'></image>
            <text class='text-lg margin-sm'>问题反馈</text>
          </button>
        </view>
      </view>

    </block>

    <view class='cu-tabbar-height'></view>

    <view class="cu-bar tabbar bg-white tn-footerfixed">
      <view class="action">
        <view class='cuIcon-cu-image'>
          <image src='/static/images/tabbar/a2.png'></image>
        </view>
        <view class='text-gray'>首页</view>
      </view>
      <view class="action">
        <view class='cuIcon-cu-image'>
          <image src='/static/images/tabbar/b2.png'></image>
        </view>
        <view class='text-gray'>简链</view>
      </view>
      <view class="action">
        <view class='cuIcon-cu-image'>
          <image src='/static/images/tabbar/c2.png'></image>
        </view>
        <view class='text-gray'>简历</view>
      </view>
      <view class="action">
        <view class='cuIcon-cu-image'>
          <image src='/static/images/tabbar/d1.png'></image>
        </view>
        <view class='text-blue'>我的</view>
      </view>
    </view>

  </view>
</template>

<script>
  export default {
    data() {
      return {
        Custom: this.Custom,
        CustomBar: this.CustomBar,
        pic: [{
          link: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1551924824231-assets/web-upload/5fd0d7ff-f3a9-41d5-a166-220621437c10.jpeg',
          name: '春天'
        }, {
          link: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1551833865977-assets/web-upload/326b9a70-7630-438b-89e4-f38e8cfafa2e.jpeg',
          name: '夏天'
        }, {
          link: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1551799188571-assets/web-upload/9d73921e-c4e4-4231-a07a-4caca0d26b64.jpeg',
          name: '秋天'
        }, {
          link: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1552028807382-assets/web-upload/27c0aad7-f2b0-4ed4-a1f2-fa640463dd85.jpeg',
          name: '冬天'
        }],
        topBackGroupImageIndex: 1
      }
    },
    methods: {
      switchImage(index) {
        this.topBackGroupImageIndex = index
      }
    }
  }
</script>

<style lang="scss" scoped>
  .UCenter-bg {
    background: #fff;
    background-size: 100% 100%;
    /* background-size: cover; */
    height: 550rpx;
    display: flex;
    justify-content: center;
    padding-top: 40rpx;
    overflow: hidden;
    position: relative;
    flex-direction: column;
    align-items: center;
    color: #fff;
    font-weight: 300;
    text-shadow: 0 0 3px rgba(0, 0, 0, 0.3);
  }

  .UCenter-bg text {
    opacity: 0.8;
  }

  .UCenter-bg image {
    width: 200rpx;
    height: 200rpx;
  }

  .UCenter-bg .gif-wave {
    position: absolute;
    width: 100%;
    bottom: 0;
    left: 0;
    z-index: 99;
    mix-blend-mode: screen;
    height: 100rpx;
  }

  map,
  .mapBox {
    left: 0;
    z-index: 99;
    mix-blend-mode: screen;
    height: 100rpx;
  }

  map,
  .mapBox {
    width: 750rpx;
    height: 300rpx;
  }

  /* ===
       编辑按钮
=== */

  .edit {
    position: fixed;
    width: 100rpx;
    height: 100rpx;
    bottom: 200rpx;
    right: 30rpx;
    z-index: 1024;
    opacity: 0.8;
    border: 1px solid #189eff;
    border-radius: 100rpx;
    box-shadow: 0rpx 0rpx 6rpx rgba(24, 158, 255, 1);
    padding: 20rpx;
  }

  /* ===
        头像
=== */

  .cu-avatar {
    font-variant: small-caps;
    margin: 0;
    padding: 0;
    display: inline-flex;
    text-align: center;
    justify-content: center;
    align-items: center;
    background: #ccc;
    color: #fff;
    white-space: nowrap;
    position: relative;
    width: 150rpx;
    height: 150rpx;
    background-size: cover;
    background-position: center;
    vertical-align: middle;
    font-size: 1.5em;
    z-index: 0;
  }

  .name {
    text-shadow: 2px 2px 1px #2f9bfe;
  }

  .dialog2 {
    background: none;
  }

  .saicode {
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
  }

  .img-big image {
    top: -40px;
    width: 280rpx;
    height: 280rpx;
  }


  .shadow-me {
    box-shadow: 0rpx 0rpx 100rpx 0rpx rgba(0, 0, 0, 0.1);
  }

  .tn-footerfixed {
    position: fixed;
    width: 100%;
    bottom: 0;
    z-index: 1024;
    box-shadow: 0 1rpx 6rpx rgba(0, 0, 0, 0.35);
  }







  .my_logo {
    background: none;
    padding: 50rpx 0 30rpx 0;
  }

  .my-radius {
    border-radius: 12rpx;
    overflow: hidden
  }

  .my-icon image {
    width: 100rpx;
    height: 100rpx;
    display: inline-block;
    margin: 0 auto
  }

  .my-iconcolor {
    background: rgba(255, 255, 255, 0.96)
  }

  .shadow-shop {
    box-shadow: 0rpx 0rpx 90rpx 0rpx rgba(0, 0, 0, 0.1);
  }

  .qrcode-img {
    position: fixed;
    width: 80rpx;
    height: 80rpx;
    bottom: 350rpx;
    right: 30rpx;
    z-index: 1024;
    opacity: 0.8;
    box-shadow: 0rpx 8rpx 30rpx 0rpx rgba(0, 0, 0, 0.3);
    border: none
  }


  /* 数字背景 */
  .shadow-warp-my {
    position: relative;
    box-shadow: 0 10rpx 10rpx rgba(0, 0, 0, 0.1);
  }

  .shadow-warp-my:before,
  .shadow-warp-my:after {
    position: absolute;
    content: "";
    top: 20rpx;
    bottom: 30rpx;
    left: 20rpx;
    width: 50%;
    box-shadow: 0 30rpx 20rpx rgba(0, 0, 0, 0.16);
    transform: rotate(-6deg);
    z-index: -1;
  }

  .shadow-warp-my:after {
    right: 20rpx;
    left: auto;
    transform: rotate(6deg);
  }


  .bg-product {
    background-image: linear-gradient(rgba(0, 0, 0, 0.06), rgba(0, 0, 0, 0));
    color: #fff;
  }

  .margin-bottom-my {
    margin-bottom: 150rpx;
  }
</style>
