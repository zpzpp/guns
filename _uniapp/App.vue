<script>
	import Vue from 'vue'
	import {
		verifyToken
	} from '@/utils/token';
	export default {
		onLaunch: function() {
			//登录监测
			// verifyToken()
			uni.getSystemInfo({
				success: function(e) {
					// #ifndef H5
					// 获取手机系统版本
					const system = e.system.toLowerCase()
					const platform = e.platform.toLowerCase()
					// 判断是否为ios设备
					if (platform.indexOf('ios') != -1 && (system.indexOf('ios') != -1 || system.indexOf(
							'macos') != -1)) {
						Vue.prototype.SystemPlatform = 'apple'
					} else if (platform.indexOf('android') != -1 && (system.indexOf('android') != -1)) {
						Vue.prototype.SystemPlatform = 'android'
					} else {
						Vue.prototype.SystemPlatform = 'devtools'
					}
					// #endif
					// #ifndef MP
					Vue.prototype.StatusBar = e.statusBarHeight;
					if (e.platform == 'android') {
						Vue.prototype.CustomBar = e.statusBarHeight + 50;
					} else {
						Vue.prototype.CustomBar = e.statusBarHeight + 45;
					};
					// #endif

					// #ifdef MP-WEIXIN
					Vue.prototype.StatusBar = e.statusBarHeight;
					let custom = wx.getMenuButtonBoundingClientRect();
					Vue.prototype.Custom = custom;
					Vue.prototype.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
					// #endif		

					// #ifdef MP-ALIPAY
					Vue.prototype.StatusBar = e.statusBarHeight;
					Vue.prototype.CustomBar = e.statusBarHeight + e.titleBarHeight;
					// #endif
				}
			})

			// #ifdef MP-WEIXIN
			//更新检测
			if (wx.canIUse('getUpdateManager')) {
				const updateManager = wx.getUpdateManager();
				updateManager.onCheckForUpdate((res) => {
					if (res.hasUpdate) {
						updateManager.onUpdateReady(() => {
							uni.showModal({
								title: '更新提示',
								content: '新版本已经准备就绪，是否需要重新启动应用？',
								success: (res) => {
									if (res.confirm) {
										updateManager.applyUpdate();
									}
								}
							});
						});

						updateManager.onUpdateFailed(() => {
							uni.showModal({
								title: '已有新版本上线',
								content: '小程序自动更新失败，请删除该小程序后重新搜索打开哟~~~',
							});
						});
					} else {
						//没有更新
					}
				});
			} else {
				uni.showModal({
					title: '提示',
					content: '当前微信版本过低，无法使用该功能，请更新到最新的微信后再重试。',
				});
			}
			// #endif


			// 默认颜色样式
			Vue.prototype.ColorList = [{
					title: '嫣红',
					name: 'red',
					color: '#e54d42'
				},
				{
					title: '桔橙',
					name: 'orange',
					color: '#f37b1d'
				},
				{
					title: '明黄',
					name: 'yellow',
					color: '#fbbd08'
				},
				{
					title: '橄榄',
					name: 'olive',
					color: '#8dc63f'
				},
				{
					title: '森绿',
					name: 'green',
					color: '#39b54a'
				},
				{
					title: '天青',
					name: 'cyan',
					color: '#1cbbb4'
				},
				{
					title: '海蓝',
					name: 'blue',
					color: '#0081ff'
				},
				{
					title: '姹紫',
					name: 'purple',
					color: '#6739b6'
				},
				{
					title: '木槿',
					name: 'mauve',
					color: '#9c26b0'
				},
				{
					title: '桃粉',
					name: 'pink',
					color: '#e03997'
				},
				{
					title: '棕褐',
					name: 'brown',
					color: '#a5673f'
				},
				{
					title: '玄灰',
					name: 'grey',
					color: '#8799a3'
				},
				{
					title: '草灰',
					name: 'gray',
					color: '#aaaaaa'
				},
				{
					title: '墨黑',
					name: 'black',
					color: '#333333'
				},
				{
					title: '雅白',
					name: 'white',
					color: '#ffffff'
				},
			]
		},
		onShow: function() {

		},
		onHide: function() {

		},
		globalData: {
			isUpdateUserInfo: false, //是否需要更新用户信息
			verifySuccess: false, //标记是否验证Token成功
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import url("colorui/main.css");
	@import url("colorui/icon.css");
	@import url("static/css/nav.css");
	@import url("static/css/tuniao.css");
	@import url("static/css/label.css");

	page {
		background-color: #FFFFFF;
	}

	.cu-bar {
		.text-color {
			// 默认字体颜色
			color: #AAAAAA;
		}

		.text-select-color {
			// 被选中时颜色
			color: #FBBD12;
		}

		.icon-bg {
			// icon的默认配色（不是图片icon）
			background-color: #FBBD12;
			color: #FFFFFF;
		}
	}
</style>
