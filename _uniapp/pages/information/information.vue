<template>
  <view class="information">
    <tn-custom bg-color="bg-white shadow">
      <block slot="left">
        <view class='action'>
          <text class="text-xxl text-black">资讯</text>
        </view>
      </block>
    </tn-custom>

    <view class="cu-card article no-card padding-top">
      <view class="cu-item" v-for="(item, index) in paginateData" :key="index">
        <view class="content margin-top-sm" @click="navDetail(item.id)">
          <image class="product-border solid" :src="item.main_image.prefix" mode="aspectFill"></image>
          <view class="desc" style="margin-top:-8rpx">
            <view class='text-content product-title'>
              <view class='text-black name-title-b text-xl'>{{item.title}}</view>

              <view class='text-gray name-title-a text-lg padding-top-xs'>{{item.desc}}</view>
            </view>

            <view class="cu-list  price price-product text-title text-lg text-red" style="margin-top:-8rpx">
              <view class='flex justify-between'>
                <view :class="'cu-tag bg-label' + (index%11+1) + ' light round margin-right-sm text-df padding text-bold'">{{item.category.title}}</view>
                <view class="margin-top-xs text-gray opacity-a">
                  <text class="cuIcon-attentionfill"></text> {{item.view_count}}
                  <text class="cuIcon-appreciatefill margin-left-sm"></text> {{item.like_count}}
                </view>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  import {
    getInformationCategoryPaginationData
  } from "@/api/information";

  export default {
    name: 'Information',
    data() {
      return {
        paginateData: [],
        paginateCacheData: [],
        category_id: 0, // 当前获取数据的栏目id
        pageIndex: 1, // 当前数据的页码
        isLoadAll: false, //是否全部已经加载完毕
      }
    },
    created() {
      this._loadData();
    },
    methods: {
      // 跳转到资讯详情
      navDetail(id) {
        wx.navigateTo({
          url: '/pages/information-detail/information-detail?id=' + id,
        });
      },

      // 从下往上拉动进行加载分页数据
      handleNextPage() {
        if (!this.isLoadAll) {
          this.pageIndex++;
          this._loadPaginateData(true);
        }
      },

      // 加载数据
      _loadData(callback) {

        this._loadPaginateData();
      },

      /**
       * 加载栏目的数据
       * params:
       * buttom_refresh 标记是否为上滑加载
       */
      _loadPaginateData(buttom_refresh = false) {
        uni.showLoading({
          title: '加载中...',
          mask: true
        });
        const category_id = this.category_id;
        // console.log(buttom_refresh);
        this._getPaginationData(category_id).then((res) => {
          uni.hideLoading()
          //判断是否还有数据返回，如果没有则证明已经全部加载完成
          if (res.data && res.data.length > 0) {
            // 合并数组
            this.paginateData.push.apply(this.paginateData, res.data);

          } else {
            this.isLoadAll = true; //全部加载完毕
            this.pageIndex = 1;
          }
        })
      },

      /**
       * 获取资讯的分页数据
       */
      _getPaginationData(category_id) {
        return new Promise((resolve, reject) => {
          getInformationCategoryPaginationData({
            category_id: category_id,
            page: this.pageIndex,
            limit: 10,
            type: 'information'
          }).then((res) => {
            resolve(res.data);
          }).catch((res) => {
            if (res.errorCode != 40103) {
              wx.showToast({
                title: '加载失败',
                icon: 'none',
              });
            }
            resolve({
              data: []
            });
          });
        });
      }
    }
  }
</script>

<style scoped lang="scss">
  .cu-card.article>.cu-item .content>image {
    height: 240rpx;
  }
</style>
