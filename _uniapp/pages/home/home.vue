<template>
	<view class="home">
		<tn-custom bg-color="bg-white">
			<block slot="left">
				<!-- <navigator class='padding-left-xs' url="/pages/about/about" hover-class="none">
					<view class="cu-avatar round shadow " :style="'background-image:url(' + logoImage + ');'"></view>
				</navigator> -->

				<view class="cu-avatar round margin-right-sm shadow bg-img open-data">
					<open-data type="userAvatarUrl"></open-data>
				</view>
			</block>
			<block slot="content">
				<text class="text-xxl text-black" :style="'opacity:' +  (1 - titleOpacity) + ';'">市政院技术标准</text>
			</block>
			<block slot="right">
				<view :style="'opacity:' +  titleOpacity + ';display:'+ (titleOpacity < 1 ? 'none' : '')+ ';'">
					<navigator url="/pages/search/search" hover-class="none">
						<view class='search-form search-round search-index'>
							<text class="cuIcon-search text-bold text-lg"></text>
							<input class="" disabled type="text" placeholder="搜点什么" confirm-type="search"
								placeholder-style="color:#000"></input>
						</view>
					</navigator>
				</view>
			</block>
		</tn-custom>
		<view class="cu-bar bg-white margin-top" id="greet_view">
			<navigator url="/pages/search/search" hover-class="none" style="width: 100%;">
				<view class='search-form search-round-all search-index'>
					<text class="cuIcon-search text-bold text-lg"></text>
					<input class="" disabled type="text" placeholder="搜索" confirm-type="search"
						placeholder-style="color:#000"></input>
				</view>
			</navigator>
		</view>
		<view class="margin">
			<view class="cu-list grid col-3 no-border index-iconcolor shadow-lg index-radius cu-list-no shadow-shop">
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/dizhi.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">地址导航</text>
					</navigator>
				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/shoucang.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">官网网址</text>
					</navigator>
				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/about.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">关于我们</text>
					</navigator>

				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/dizhi.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">地址导航</text>
					</navigator>
				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/shoucang.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">官网网址</text>
					</navigator>
				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/about.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">关于我们</text>
					</navigator>

				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/dizhi.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">地址导航</text>
					</navigator>
				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/shoucang.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">官网网址</text>
					</navigator>
				</view>
				<view class="cu-item index-icon">
					<navigator url="" delta="1" hover-class="none">
						<image src='/static/images/about.png' class='png' mode='aspectFit'></image>
						<text class="text-xl text-index">关于我们</text>
					</navigator>

				</view>
			</view>
		</view>
		<view class="cu-bar bg-white margin-top-sm">
			<view class='action'>
				<text class='text-bold text-title text-xxl'>最新发布</text>
			</view>
			<view class='action text-xl opacity-a' @click="navAllCase">
				查看更多
				<text class='cuIcon-magic margin-left-xs'> </text>
			</view>
		</view>

		<view class="cu-card bg-white article no-card">
			<view class="cu-item solid-top" v-for="(item, index) in paginateData" :key="index">
				<view class="content margin-top-sm" @click="navDetail(item.id)">
					<!-- <image class="product-border solid" :src="item.main_image.prefix" mode="aspectFill"></image> -->
					<view class="desc" style="margin-top:-8rpx">
						<view class='text-content'>
							<view class='text-black name-title-a text-xl tn-align'>{{item.title}}</view>

							<view class='text-gray name-title-b text-lg padding-top-xs tn-align'>{{item.desc}}</view>
						</view>

						<view class="cu-list  price price-product text-title text-lg text-red" style="margin-top:-8rpx">
							<view class='flex justify-between'>
								<view
									:class="'cu-tag bg-label' + (index%11+1) + ' light round margin-right-sm text-xs padding-xs text-bold'">
									{{item.category.title}}
								</view>
								<view class="margin-top-xs text-gray opacity-a">
									<text class="cuIcon-attention margin-right-xs"></text> {{item.view_count}}
									<text class="cuIcon-favor margin-left-sm margin-right-xs"></text>{{item.like_count}}
								</view>
							</view>
						</view>
					</view>
					<!-- align-center cuIcon-favor-->
					<view class="flex justify-center  text-xl "  @click.stop="likeStandard(item.id)"> 
						<text class="cuIcon-favorfill margin-left-sm margin-right-xs product-border text-yellow"></text>
					</view>
				</view>
			</view>
		</view>


		<view class="cu-bar bg-white margin-top" id="greet_view1">
			<view class='action'>
				<text class='text-bold text-title text-xxl'>{{greeting}}</text>
			</view>
			<!-- <view class='action text-xxl'>
    		<text class='text-bold text-black icon-share margin-right-xs'> </text>
    	</view> -->
			<view class='action'>
				<button class='cu-btn bg-white text-xxl' open-type="share" style="margin-right:-14px">
					<text class='text-bold text-black cuIcon-share'></text>
				</button>

			</view>
		</view>


		<view>
			<swiper class="padding radius-index screen-swiper square-dot" indicator-dots="true" circular="true"
				autoplay="true" interval="5000" duration="500">
				<swiper-item v-for="(item, index) in bannerArr" :key="index" class="radius-index">
					<image class='' :src="item" mode='aspectFill'></image>
				</swiper-item>
			</swiper>
		</view>




		<block>
			<view class="padding-sm bg-white">
				<view class="flex">
					<view v-if="newestCase[0]" @click="navCaseDetail(newestCase[0].id)"
						class='flex-twice bg-white margin-xs bg-img shadow-blur radius-index solid'
						:style="'height:330rpx;background-image:url(' + newestCase[0].imgUrl + ')'">
					</view>
					<view class='flex-sub radius margin-right-xs margin-left-sm'>
						<view v-if="newestCase[1]" @click="navCaseDetail(newestCase[1].id)"
							class='flex-sub bg-white margin-top-xs bg-img shadow-blur radius-index solid'
							:style="'height:150rpx;background-image:url(' + newestCase[1].imgUrl + ')'">
						</view>
						<view v-if="newestCase[2]" @click="navCaseDetail(newestCase[2].id)"
							class='flex-sub bg-white margin-top bg-img shadow-blur radius-index solid'
							:style="'height:150rpx;background-image:url(' + newestCase[2].imgUrl + ')'">
						</view>
					</view>
				</view>
			</view>
		</block>


		<view class="cu-bar bg-white margin-top-xl">
			<view class='action'>
				<text class='text-bold text-title text-xxl'>业务范围</text>
			</view>
			<!-- <view class='action text-xl'>
        全部 <text class='icon-magic margin-left-xs'> </text>
      </view> -->
		</view>


		<view class='nav-list margin-top'>
			<view v-if="businessTitle[0]" @click="navBusinessDetail(businessTitle[0].id)" class="nav-li bg-index1">
				<view class="nav-title">图鸟</view>
				<view class="nav-name">{{businessTitle[0].title}}</view>
				<text class='cuIcon-wefill'></text>
			</view>
			<view v-if="businessTitle[1]" @click="navBusinessDetail(businessTitle[1].id)" class="nav-li bg-index2">
				<view class="nav-title">图鸟</view>
				<view class="nav-name">{{businessTitle[1].title}}</view>
				<text class='cuIcon-weixin'></text>
			</view>
			<view v-if="businessTitle[2]" @click="navBusinessDetail(businessTitle[2].id)" class="nav-li bg-index3">
				<view class="nav-title">图鸟</view>
				<view class="nav-name">{{businessTitle[2].title}}</view>
				<text class='cuIcon-card'></text>
			</view>
			<view v-if="businessTitle[3]" @click="navBusinessDetail(businessTitle[3].id)" class="nav-li bg-index4">
				<view class="nav-title">图鸟</view>
				<view class="nav-name">{{businessTitle[3].title}}</view>
				<text class='cuIcon-skinfill'></text>
			</view>
		</view>
		<!-- <view class="shop" @click="navMpShopProductList">
			<view class="bg0 pa">
				<view class="bg1">
					<image src="/static/images/shop1.png" class="button-shop shadow"></image>
				</view>
			</view>
			<view class="hx-box pa">
				<view class="pr">
					<view class="hx-k1 pa0">
						<view class="span"></view>
					</view>
					<view class="hx-k2 pa0">
						<view class="span"></view>
					</view>
					<view class="hx-k3 pa0">
						<view class="span"></view>
					</view>
					<view class="hx-k4 pa0">
						<view class="span"></view>
					</view>
					<view class="hx-k5 pa0">
						<view class="span"></view>
					</view>
					<view class="hx-k6 pa0">
						<view class="span"></view>
					</view>
				</view>
			</view>
		</view> -->
	</view>
</template>
RG
<script>
	import {
		getServerConfigValue
	} from '@/api/server-config';
	import {
		getBannerData
	} from '@/api/banner';
	import {
		getCaseRecommData
	} from '@/api/case';
	import {
		getDData
	} from '@/api/business';


	const util = require('@/utils/util')

	export default {
		name: 'Home',
		props: {
			show: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				titleOpacity: 0, // 标题透明度
				greeting: '', //问候语
				logoImage: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1568101679032-assets/web-upload/ee18e6f9-6735-4cf1-a628-f2b4234dc1dc.jpeg', //'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1568100394340-assets/web-upload/792c999f-bf08-41d5-a33b-21fc5bb263a2.jpeg', //左上角logo的图片
				bannerArr: [
					'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574735460307-assets/web-upload/b47753a0-58db-4bf3-a97b-340ddba70eef.jpeg',
					'https://cdn.nlark.com/yuque/0/2019/png/280373/1574735459784-assets/web-upload/db11b70f-0b2c-4b20-9e03-b650d7f0fe06.png',
					'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574735459657-assets/web-upload/924619aa-c0f9-426e-8ba6-b7392fbe53c8.jpeg',
					'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574735460224-assets/web-upload/8c310851-15a3-4f48-a82c-9d7a1be19c33.jpeg',
				], //banner图片信息
				newestCase: [{
					id: 1,
					imgUrl: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532062-assets/web-upload/5dc61973-518a-48af-ba5d-68d6eeee5c4a.jpeg'
				}, {
					id: 2,
					imgUrl: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532613-assets/web-upload/a9d7821d-7bc4-4d26-915d-5a1bd0ef340a.jpeg'
				}, {
					id: 3,
					imgUrl: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
				}], //推荐案例信息
				paginateData:[{
					id:1,
					main_image:{prefix:'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'},
					title:'城市道路绿化规划与设计规范',
					desc:'中华人民共和国建设部',
					category:{title:'CJJ75-97'},
					view_count:1,
					like_count:1,
				},{
					id:2,
					main_image:{prefix:'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'},
					title:'城市道路绿化规划与设计规范',
					desc:'中华人民共和国建设部',
					category:{title:'CJJ75-97'},
					view_count:1,
					like_count:1,
				},{
					id:3,
					main_image:{prefix:'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'},
					title:'城市道路绿化规划与设计规范',
					desc:'中华人民共和国建设部',
					category:{title:'CJJ75-97'},
					view_count:1,
					like_count:1,
				},{
					id:4,
					main_image:{prefix:'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'},
					title:'城市道路绿化规划与设计规范',
					desc:'中华人民共和国建设部',
					category:{title:'CJJ75-97'},
					view_count:1,
					like_count:1,
				}],
				businessTitle: [{
					id: 1,
					title: 'UI设计'
				}, {
					id: 2,
					title: '微信小程序'
				}, {
					id: 3,
					title: '网站开发'
				}, {
					id: 4,
					title: '定制开发'
				}], //业务范围标题
			}
		},
		created() {
			console.log("首页created")
			this._loadData();
		},
		updated() {
			console.log("首页updated")
		},
		methods: {
			// 跳转到业务详情
			navBusinessDetail(id) {
				uni.navigateTo({
					url: '/pages/business-detail/business-detail?id=' + id,
				});
			},

			// 跳转到案例详情
			navCaseDetail(id) {
				uni.navigateTo({
					url: '/pages/case-detail/case-detail?id=' + id,
				});
			},

			// 跳转到全部案例
			navAllCase() {
				this.$emit('switchTab', 'case')
			},

			// 跳转到搜索
			navSearch(e) {
				this.$emit('switchTab', 'search')
				// uni.navigateTo({
				// 	url: '/pages/search/search',
				// });
			},

			// 加载必要的数据
			_loadData(callback) {

				uni.showLoading({
					title: '加载中',
					mask: true,
				});

				// 获取对应的问候语
				this.greeting = util.timeGreeting(new Date())
				/* uni.request({
		    url:  'http://localhost:8080/sysConfig/list',
		    method: 'GET',
			header: {
              'content-type': 'application/json',
              'Authorization': uni.getStorageSync('auth_openid')
          },
		    success: (res) => {
		       console.log(res)
			   debugger
		    }
		}) */

				/* Promise.all([
					// this._getServerConfig(),
					// this._getBannerData(),
					// this._getRecommCase(),
					// this._getBusinessTitle()
				]).then(([server_config, banner_data, recomm_case, business_title]) => {
					this.logoImage = server_config.hasOwnProperty('mp_index_logo') ? server_config.mp_index_logo :
						'',
						this.bannerArr = banner_data,
						this.newestCase = recomm_case,
						this.businessTitle = business_title
				}).then(() => {
					uni.hideLoading();
				}).catch(() => {
					uni.showModal({
						title: '加载错误',
						content: '数据加载错误，请关闭小程序后重试',
					});
				}) */
				uni.hideLoading();
			},

			/**
			 * 获取页面组件对应的信息
			 * params:
			 * params.name 节点的标识
			 * params.success 成功后回调函数
			 */
			handleSearchInputShow(params) {
				this.$getComponentInfo({
					component: this,
					name: '#greet_view',
					success: rect => {
						const height = this.CustomBar - rect.top
						let opacity = parseFloat((height / this.CustomBar).toFixed(2))
						// console.log(opacity);
						// console.log(height,height / 59);
						// opacity = 100 / height / 100 * e.scrollTop
						this.titleOpacity = opacity > 1 ? 1 : opacity
					}
				})
			},

			// 获取左上角的图标
			_getServerConfig() {
				return new Promise((resolve, reject) => {
					getServerConfigValue({
						field: 'mp_index_logo'
					}).then((res) => {
						resolve(res.data);
					}).catch(() => {
						resolve({});
					})
				});
			},

			// 获取轮播图数据
			_getBannerData() {
				return new Promise((resolve, reject) => {
					getBannerData({
						banner_pos: 1,
						limit: 4
					}).then((res) => {
						resolve(res.data);
					}).catch(() => {
						resolve([]);
					})
				});
			},

			//获取最新案例数据
			_getRecommCase() {
				return new Promise((resolve, reject) => {
					getCaseRecommData({
						limit: 3
					}).then((res) => {
						resolve(res.data);
					}).catch(() => {
						resolve([]);
					})
				});
			},

			// 获取业务的标题
			_getBusinessTitle() {
				return new Promise((resolve, reject) => {
					getBusinessTitleData({
						limit: 4
					}).then((res) => {
						resolve(res.data);
					}).catch(() => {
						resolve([]);
					})
				});
			}
		}
	}
</script>

<style scoped>
	/* .search-index {
		background: rgba(255, 255, 255, 0.4);
	} */

	.cu-bar .content image {
		height: 60rpx;
		width: 240rpx;
	}

	.cardTitle {
		color: #fff;
		padding: 220rpx 20rpx 20rpx 20rpx;
		font-size: 40rpx;
		font-weight: 300;
		/* transform: skew(-10deg, 0deg); */
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
  }
  .cardTitle::after{
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


	.search-round {
		border-radius: 5000rpx;
		/* border-top-right-radius: 20rpx; */
		border-bottom-right-radius: 20rpx;
		/* border-bottom-left-radius: 20rpx */
	}

	.search-round-all {
		border-radius: 5000rpx;
	}


	.search-index {
		background: rgba(0, 0, 0, 0.1);
		opacity: 0.5
	}

	.text-title {
		color: #000;
	}

	.radius-index {
		overflow: hidden;
		border-radius: 10rpx;
	}

	.shadow-blur {
		overflow: initial;
	}

	.nav-title {
		opacity: 0;
	}

	.nav-li text {
		right: 0rpx;
		font-size: 100rpx;
		width: 108rpx;
		height: 108rpx;
		opacity: 0.1;
	}


	/* 悬浮按钮 */
	.button-shop {
		width: 90rpx;
		height: 90rpx;
		display: flex;
		flex-direction: row;
		position: fixed;
		/* bottom:200rpx;
      right: 20rpx; */
		left: 5rpx;
		top: 5rpx;
		z-index: 1001;
		border-radius: 100px;
		opacity: 0.9;
	}


	/* 按钮 */
	.shop {
		bottom: 300rpx;
		right: 75rpx;
		position: fixed;
		z-index: 9999;
	}


	.pa,
	.pa0 {
		position: absolute
	}

	.pa0 {
		left: 0;
		top: 0
	}


	.bg0 {
		width: 100rpx;
		height: 100rpx;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}

	.bg1 {
		width: 100%;
		height: 100%;
	}

	.cu-card.article>.cu-item .content>image {
		height: 180rpx;
	}
	.cu-card.article>.cu-item {
		padding-bottom: 10rpx;
		padding-top: 5rpx;
	}

	.hx-box {
		top: 50%;
		left: 50%;
		width: 100rpx;
		height: 100rpx;
		transform-style: preserve-3d;
		transform: translate(-50%, -50%) rotateY(75deg) rotateZ(10deg);
	}

	.hx-box .pr {
		width: 100rpx;
		height: 100rpx;
		transform-style: preserve-3d;
		animation: hxz 20s linear infinite;
	}

	@keyframes hxz {
		0% {
			transform: rotateX(0deg);
		}

		100% {
			transform: rotateX(-360deg);
		}
	}



	.hx-box .pr .pa0 {
		width: 100rpx;
		height: 100rpx;
		/* border: 4px solid #5ec0ff; */
		border-radius: 1000px;
	}

	.hx-box .pr .pa0 .span {
		display: block;
		width: 100%;
		height: 100%;
		background: url(https://z3.ax1x.com/2021/07/06/RoKanS.png) no-repeat center center;
		background-size: 100% 100%;
		animation: hx 4s linear infinite;
	}

	@keyframes hx {
		to {
			transform: rotate(360deg);
		}
	}

	.hx-k1 {
		transform: rotateX(-60deg) rotateZ(-60deg)
	}

	.hx-k2 {
		transform: rotateX(-30deg) rotateZ(-30deg)
	}

	.hx-k3 {
		transform: rotateX(0deg) rotateZ(0deg)
	}

	.hx-k4 {
		transform: rotateX(30deg) rotateZ(30deg)
	}

	.hx-k5 {
		transform: rotateX(60deg) rotateZ(60deg)
	}

	.hx-k6 {
		transform: rotateX(90deg) rotateZ(90deg)
	}
</style>
