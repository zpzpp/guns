<template>
	<view class="case">
		<tn-custom bg-color="bg-white">
			<block slot="left">
				<view class='action'>
					<text class="text-xxl text-black">案例</text>
				</view>
			</block>
		</tn-custom>
		<scroll-view v-if="showNavScroll" scroll-x="true" class="bg-white nav fixed" scroll-with-animation="true"
			:scroll-left="scrollLeft" :style="'padding-top:' + (CustomBar + 15) + 'px;'">
			<block v-for="(item, index) in caseNav" :key="index">
				<view :class="['cu-item text-lg', index == TabCur ? ' text-black cur text-bold text-xl' : '']"
					@click='navSelect(item.id, index)'>
					{{item.title}}
				</view>
			</block>
		</scroll-view>
		<mescroll-body ref="mescrollRef" top="125" @init="mescrollInit" @down="downCallback" @up="upCallback"
			:down="downOption" :up="upOption">

			<view class="cu-card article no-card padding-top">
				<view class="cu-item" v-for="(item, index) in paginateData" :key="index">
					<view class="content margin-top-sm" @click="navDetail(item.id)">
						<view class="desc" style="margin-top:-8rpx">
							<view class='text-content'>
								<view class='text-black name-title-a text-xl tn-align'>{{item.title}}</view>

								<view class='text-gray name-title-b text-lg padding-top-xs tn-align'>{{item.desc}}
								</view>
							</view>

							<view class="cu-list  price price-product text-title text-lg text-red"
								style="margin-top:-8rpx">
								<view class='flex justify-between'>
									<view
										:class="'cu-tag bg-label' + (index%11+1) + ' light round margin-right-sm text-df padding text-bold'">
										{{item.category.title}}</view>
									<view class="margin-top-xs text-gray opacity-a">
										<text class="cuIcon-attentionfill margin-right-xs"></text> {{item.view_count}}
										<text class="cuIcon-appreciatefill margin-left-sm margin-right-xs"></text>
										{{item.like_count}}
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>


		</mescroll-body>
		<!-- <view class="cu-card article no-card padding-top-sm"
			:style="'padding-top:' + CustomBar + 'px;padding-bottom:' + CustomBar + 'px;'"
			v-if="isLoadAll && categoryData.length == 0">
			<view class="cu-item">
				<view class="text-center text-xxl text-black ">
					<view class="">
						<image class="" mode="aspectFit" src="/static/images/product.png">
						</image>
					</view>
					暂无案例
				</view>
			</view>
		</view>
		<view v-else class="" :style="'padding-top:' + CustomBar + 'px;'">
			<block class="" v-for="(item, index) in categoryData" :key="index">
				<view class="cu-card">
					<view @click="navDetail(item.id)" class="cu-item bg-img shadow-blur"
						:style="'background-image:url(' + item.main_image.prefix + ')'">
						<view class="cardTitle bg-shadeBottom">
							<text class="name-title-a">{{item.title}}</text>
						</view>
					</view>
				</view>
			</block>
		</view> -->
		<!-- <view class="cu-load text-gray" :class="!true?'loading':'over'"></view> -->
		<!-- <view class="flex-sub text-center" v-if="isLoadAll">
			<view class="solid-top text-df padding text-gray">已加载全部</view>
		</view> -->
		<!-- 已加载全部 -->
	</view>
</template>

<script>
	import {
		getCategoryNavData
	} from '@/api/category';
	import {
		getCaseCategoryPaginationData
	} from '@/api/case';

	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";


	export default {
		name: 'Case',
		props: {
			show: {
				type: Boolean,
				default: false
			}
		},
		mixins: [MescrollMixin], // 使用mixin

		data() {
			return {
				CustomBar: this.CustomBar,
				TabCur: 0,
				caseNav: [{
					id: 1,
					title: '全部'
				}, {
					id: 2,
					title: '小程序'
				}, {
					id: 3,
					title: '网站'
				}, {
					id: 4,
					title: 'APP'
				}, {
					id: 4,
					title: 'UI设计'
				}],
				showNavScroll: true,
				scrollLeft: 0,
				categoryData: [], // 案例栏目数据
				categoryCacheData: [], // 案例栏目缓存数据
				category_id: 0, // 当前获取数据的栏目id
				pageIndex: 1, // 当前数据的页码
				isLoadAll: false, //是否全部已经加载完毕
				mescroll: null, // mescroll实例对象 (此行可删,mixins已默认)
				// 下拉刷新的配置(可选, 绝大部分情况无需配置)
				downOption: {},
				// 上拉加载的配置(可选, 绝大部分情况无需配置)
				upOption: {
					// page: {
					// 	size: 10 // 每页数据的数量,默认10
					// },

					textNoMore: '已加载全部', // 没有更多数据的提示文本
					noMoreSize: 4, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					empty: {
						tip: '~ 暂无数据 ~', // 提示
						btnText: '去看看'
					}
				},
				paginateData: [{
					id: 1,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 2,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 3,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}, {
					id: 4,
					main_image: {
						prefix: 'https://cdn.nlark.com/yuque/0/2019/jpeg/280373/1574736532111-assets/web-upload/e80331b5-3172-4c01-930d-0077f2198835.jpeg'
					},
					title: '城市道路绿化规划与设计规范',
					desc: '中华人民共和国建设部',
					category: {
						title: 'CJJ75-97'
					},
					view_count: 1,
					like_count: 1,
				}],
				// 列表数据
				dataList: [1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2,
					1, 2, 2, 2, 1, 2, 2, 2
				]
			}
		},
		created() {
			this.TabCur = 0
			// this._loadData();
		},
		methods: {
			// 导航栏选择事件
			navSelect(id, index) {

				// 滚动条默认回到顶部
				uni.pageScrollTo({
					scrollTop: 0,
				});


				this.TabCur = index
				this.scrollLeft = (index - 1) * 60


				// 切换导航重新加载栏目数据
				this.category_id = id;
				this.pageIndex = 1;
				this.isLoadAll = false;
				this.categoryData = [];
				this._loadPaginateData(index);
			},

			switchNavScrollStatus(status) {
				this.showNavScroll = status
			},

			// 跳转到案例详情
			navDetail(id) {
				uni.navigateTo({
					url: '/pages/case-detail/case-detail?id=' + id,
				});
			},
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},
			/*下拉刷新的回调, 有3种处理方式:*/
			downCallback() {
				// 绝大部分情况methods中都不用写downCallback的,此时会默认调MescrollMixin的downCallback (效果同第2种)

				// 第1种: 请求具体接口
				/* uni.request({
					url: 'xxxx',
					success: () => {
						// 请求成功,隐藏加载状态
						this.mescroll.endSuccess()
					},
					fail: () => {
						// 请求失败,隐藏加载状态
						this.mescroll.endErr()
					}
				}) */
				// 第2种: 下拉刷新和上拉加载调同样的接口, 则不用第1种, 直接mescroll.resetUpScroll()即可
				this.mescroll.resetUpScroll(); // 重置列表为第一页 (自动执行 page.num=1, 再触发upCallback方法 )
				// 第3种: 下拉刷新什么也不处理, 可直接调用或者延时一会调用 mescroll.endSuccess() 结束即可
				// this.mescroll.endSuccess()
			},
			/*上拉加载的回调*/
			upCallback(page) {
				console.log(page)
				let _this = this;
				setTimeout(function() {

					_this.mescroll.endSuccess(5);
					// _this.mescroll.endErr();
				}, 3000);

			},

			// 从下往上拉动进行加载分页数据
			handleNextPage() {
				if (!this.isLoadAll) {
					this.pageIndex++;
					this._loadPaginateData(this.TabCur, true);
				}
			},

			// 加载必要的数据
			_loadData(callback) {
				this.caseNav = [{
					id: 0,
					title: '全部'
				}]
				// 加载导航数据
				getCategoryNavData({
					top_id: 1
				}).then((res) => {

					this.caseNav = [...this.caseNav, ...res.data]
					// 加载第一个栏目导航的数据
					this._loadPaginateData(this.TabCur);

				})
			},

			/**
			 * 加载栏目的数据
			 * params:
			 * index 缓存标号
			 * buttom_refresh 标记是否为上滑加载
			 */
			_loadPaginateData(index, buttom_refresh = false) {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				const category_id = this.category_id;
				const cacheData = this.categoryCacheData;
				// console.log(buttom_refresh);
				// 判断缓存数据中是否已有该栏目的数据
				if (cacheData[index] == null || (buttom_refresh && !cacheData[index]['isLoadAll'])) {
					this._getCasePaginationData(category_id).then((res) => {
						uni.hideLoading();
						if (res.data && res.data.length > 0) {
							// 合并数组
							this.categoryData.push.apply(this.categoryData, res.data);

						} else {
							this.isLoadAll = true; //全部加载完毕
							this.pageIndex = 1;
						}

						cacheData[index] = {
							data: this.categoryData,
							pageIndex: this.pageIndex,
							isLoadAll: this.isLoadAll
						};
					})
				} else {
					// 取出对应缓存的数据
					this.categoryData = cacheData[index]['data'];
					this.pageIndex = cacheData[index]['pageIndex'];
					this.isLoadAll = cacheData[index]['isLoadAll'];

					uni.hideLoading();
				}
			},

			// 获取case对应栏目的分页数据
			_getCasePaginationData(category_id) {
				return new Promise((resolve, reject) => {
					getCaseCategoryPaginationData({
						category_id: category_id,
						page: this.pageIndex,
						limit: 10,
						type: 'case'
					}).then((res) => {
						resolve(res.data);
					}).catch((res) => {
						if (res.errorCode != 40103) {
							uni.showToast({
								title: '加载失败',
								icon: 'none',
							});
						}
						resolve({
							data: []
						});
					})
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.nav {
		.cu-item {
			margin: 0 20rpx;
		}

		.cu-item.cur {
			border-bottom: 8rpx solid #FBBD12;
		}
	}

	.bg-shadeBottom {
		border-radius: 10rpx;
	}

	.product-title {
		padding-left: 5rpx;
	}

	.price {
		display: inline-flex;
		vertical-align: bottom;
	}

	.price-product {}

	.delete-price {
		padding-left: 10rpx;
		text-decoration: line-through
	}

	.product-border {
		width: 1em;
		height: 1em;
		border: 1rpx solid rgba(0, 0, 0, 0.05)
	}


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
</style>
