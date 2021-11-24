<template>
  <view class="index">
    <home v-if="tabberPageLoadFlag.home" :style="{display: curPage != 'home' ? 'none' : ''}" ref="home" @switchTab="switchTab"></home>
    <case v-if="tabberPageLoadFlag.case" :style="{display: curPage != 'case' ? 'none' : ''}" ref="case"></case>
    <components v-if="tabberPageLoadFlag.components" :style="{display: curPage != 'components' ? 'none' : ''}" ref="components"></components>
    <information v-if="tabberPageLoadFlag.information" :style="{display: curPage != 'information' ? 'none' : ''}" ref="information"></information>
    <my v-if="tabberPageLoadFlag.my" :style="{display: curPage != 'my' ? 'none' : ''}" ref="my"></my>

    <view class="cu-tabbar-height"></view>
    <view class="cu-bar tabbar bg-white shadow foot">
      <view class="action" @click="navChange" data-cur="home">
        <view class='cuIcon-cu-image'>
          <image :src="'/static/images/tabbar/home' + [curPage=='home'?'_cur':''] + '.png'"></image>
        </view>
        <view :class="curPage=='home'?'text-select-color':'text-color'">首页</view>
      </view>
      <view class="action" @click="navChange" data-cur="case">
        <view class='cuIcon-cu-image'>
          <image :src="'/static/images/tabbar/case' + [curPage=='case'?'_cur':''] + '.png'"></image>
        </view>
        <view :class="curPage=='case'?'text-select-color':'text-color'">案例</view>
      </view>
      <view class="action text-color add-action" @click="navChange" data-cur="components">
        <button class="cu-btn cuIcon-wefill icon-bg shadow"></button>
        <view :class="curPage=='components'?'text-select-color':'text-color'">组件</view>
      </view>
      <view class="action" @click="navChange" data-cur="information">
        <view class='cuIcon-cu-image'>
          <image :src="'/static/images/tabbar/information' + [curPage=='information'?'_cur':''] + '.png'"></image>
        </view>
        <view :class="curPage=='information'?'text-select-color':'text-color'">咨讯</view>
      </view>
      <view class="action" @click="navChange" data-cur="my">
        <view class='cuIcon-cu-image'>
          <image :src="'/static/images/tabbar/my' + [curPage=='my'?'_cur':''] + '.png'"></image>
        </view>
        <view :class="curPage=='my'?'text-select-color':'text-color'">我的</view>
      </view>
    </view>
  </view>
</template>

<script>
  import {
    checkUserInfo,
    getUserInfo
  } from '@/utils/user';
  import {
    verifyToken
  } from '@/utils/token';
  import Home from '@/pages/home/home';
  import Case from '@/pages/case/case';
  import Components from '@/pages/components/home';
  import Information from '@/pages/information/information';
  import My from '@/pages/my/my';

  const app = getApp()

  export default {
    components: {
      Home,
      Case,
      Components,
      Information,
      My
    },
    data() {
      return {
        curPage: 'home',
        tabberPageLoadFlag: {
          home: true,
          case: false,
          components: false,
          information: false,
          my: false
        }
      }
    },
    onLoad() {
      // 用于更新用户的信息
      //判断是否已经有token，或者验证成功(程序初始化完毕)
	  verifyToken().then(resp => {
		   // console.log(resp)
		  return checkUserInfo()
	  }).then(resp => {
		  debugger
		  console.log(resp)
        // getUserInfo().catch((error) => {console.log(error)});
      }).catch((error) => {console.log(error)})
    },
    onShareAppMessage() {
      switch (this.curPage) {
        case 'home':
          return {
            title: '和东东们一起愉快的玩耍叭',
            imageUrl: '/static/images/share.jpg',
          }
          break
        case 'case':
          return {
            title: '图鸟案例',
            imageUrl: '/static/images/share.jpg',
          }
          break
        case 'components':
          return {
            title: '组件案例',
            imageUrl: '/static/images/share.jpg',
          }
          break
        case 'information':
          return {
            title: '图鸟资讯',
            imageUrl: '/static/images/share.jpg',
          }
          break
        default:
          return {
            title: '和东东们一起愉快的玩耍叭',
            imageUrl: '/static/images/share.jpg',
          }
          break
      }
    },
    onShareTimeline() {
      switch (this.curPage) {
        case 'home':
          return {
            title: '和东东们一起愉快的玩耍叭',
            imageUrl: '/static/images/share.jpg',
          }
          break
        case 'case':
          return {
            title: '图鸟案例',
            imageUrl: '/static/images/share.jpg',
          }
          break
        case 'components':
          return {
            title: '组件案例',
            imageUrl: '/static/images/share.jpg',
          }
          break
        case 'information':
          return {
            title: '图鸟资讯',
            imageUrl: '/static/images/share.jpg',
          }
          break
        default:
          return {
            title: '和东东们一起愉快的玩耍叭',
            imageUrl: '/static/images/share.jpg',
          }
          break
      }
    },
    onPageScroll() {
      switch (this.curPage) {
        case 'home':
          this.$refs.home.handleSearchInputShow()
          break
        default:
          break
      }
    },
    onReachBottom() {
      switch (this.curPage) {
        case 'case':
          this.$refs.case.handleNextPage()
          break
        case 'information':
          this.$refs.information.handleNextPage()
          break
        default:
          break
      }
    },
    methods: {
      navChange(e) {
        this.curPage = this.$getDataSet(e, 'cur')
        this.tabberPageLoadFlag[this.curPage] = true
      },
      switchTab(name) {
        this.curPage = name
        this.tabberPageLoadFlag[this.curPage] = true
      }
    }
  }
</script>

<style>
</style>
