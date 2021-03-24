<template>
  <view class="search">
    <tn-custom bg-color="bg-white shadow">
      <block slot="left">
        <navigator class='action' open-type="navigateBack" :delta="1" hover-class="none">
          <text class='cuIcon-back_android' style="font-size:48rpx"></text>
          <text class="">搜索</text>
        </navigator>
      </block>
    </tn-custom>

    <view class="cu-bar search bg-white margin-top-xl">
      <view class='search-form round search-search text-black'>
        <text class="cuIcon-search"></text>
        <input type="text" :placeholder="inputTips" confirm-type="search" :value="inputKeyword" @input="searchInput"></input>
      </view>
      <view class='action' @click="goSearch">
        <button class='cu-btn bg-gradual-orange shadow-blur round'>搜索</button>
      </view>
    </view>

    <view class="cu-bar bg-white padding-top solid-bottom">
      <view class='action'>
        最近搜索
      </view>
      <view class='action' @click="clearKeyword">
        <text class="cuIcon-delete text-red"></text>
      </view>
    </view>

    <view class='padding-sm bg-white flex flex-wrap'>
      <view class="padding-xs">
        <view v-for="(item, index) in defaultKeyword" :key="index" class='cu-tag bg-gray light round margin-bottom padding text-df'
          @click="clickDefaultKeyword">
          {{item}}
        </view>

        <view v-for="(item, index) in recentSearchKeywords" :key="index" class='cu-tag bg-gray light round margin-bottom padding text-df'
          @click="clickRecentKeyword(item)">
          {{item}}
        </view>
      </view>
    </view>

    <view class="cu-bar bg-white solid-bottom">
      <view class='action'>
        搜索结果
      </view>
    </view>
    <view id="search-result">
      <scroll-view scroll-y="true" :style="'height: ' + resultViewHeight + 'px'" @scrolltolower="resultScrollLower">
        <!--搜索结果输出 -->
        <view class="cu-list menu menu-avatar padding-left">
          <view v-for="(item, index) in resultData" :key="index" class="cu-item" @click="navDetail" :data-id="item.id"
            :data-model="item.model_id">
            <view class="cu-avatar radius df padding-img-butt" :style="'background-image:url(' + item.main_image.prefix + ');'"></view>
            <view class='content'>
              <view class='text-grey name-title-b text-lg'>{{item.title}}</view>
            </view>
          </view>
        </view>
      </scroll-view>

    </view>
  </view>
</template>

<script>
  import {
    getSearchResult
  } from '@/api/search';
  import {
    getServerConfigValue
  } from '@/api/server-config';

  const app = getApp();


  export default {
    data() {
      return {
        inputTips: '', // 输入框默认提示
        defaultKeyword: [], // 默认的历史记录
        resultViewHeight: 0, // 搜索结果view容器高度
        recentSearchKeywords: [], // 最近搜索关键词
        inputKeyword: '', // 记录输入的keyword
        resultData: [], // 存放搜索结果
        noResult: false, // 是否有搜索结果
        pageIndex: 1, // 当前数据的页码
        isLoadAll: false, //是否全部已经加载完毕
        keyword_cache_key: 'open_website_search_keyword', //存在缓存中的键名
        keyword_cache_array_max_len: 10, //保存的最大长度
      }
    },
    onLoad() {
      this._loadData();
      this._resizeResultView();
    },
    onShow() {
      this._getRecentSearchKeyword();
    },
    methods: {
      // 监听搜索输入框的输入
      searchInput(e) {
        this.inputKeyword = e.detail.value;
      },

      // 监听搜索结果的滚动事件
      resultScrollLower(e) {
        if (!this.isLoadAll) {
          this.pageIndex++;
          this._getSearchResult();
        }
      },

      // 开始搜索
      goSearch() {
        this._addRecentSearchKeyword();

        this._resizeResultView();

        this.pageIndex = 1;
        this.isLoadAll = false;
        this.resultData = [];
        this._getSearchResult();
      },

      // 清空最近搜索关键词
      clearKeyword() {
        uni.setStorageSync(this.keyword_cache_key, []);
        this.recentSearchKeywords = []
      },

      // 点击默认的搜索历史
      clickDefaultKeyword() {
        uni.navigateTo({
          url: '/pages/about/about',
        });
      },

      // 点击最近的搜索历史
      clickRecentKeyword(keyword) {
        this.inputKeyword = keyword

        this.goSearch();
      },

      // 跳转到详情页
      navDetail(e) {
        const model_id = this.$getDataSet(e, 'model');
        const id = this.$getDataSet(e, 'id');

        if (model_id == 1) {
          uni.navigateTo({
            url: '/pages/case-detail/case-detail?id=' + id,
          });
        } else {
          uni.navigateTo({
            url: '/pages/information-detail/information-detail?id=' + id,
          });
        }
      },

      // 加载数据
      _loadData() {
        this._getSearchServerConfig();
      },

      // 获取搜索结果
      _getSearchResult() {
        uni.showLoading({
          title: '加载中',
          mask: true,
        })
        getSearchResult({
          page: this.pageIndex,
          limit: 6,
          keyword: this.inputKeyword,
        }).then((res) => {
          uni.hideLoading();
          const {
            data: {
              data: search_data
            }
          } = res
          if (search_data && search_data.length > 0) {
            this.resultData.push.apply(this.resultData, search_data);
          } else {
            this.isLoadAll = true;
            this.pageIndex = 1;
          }
        }).catch((res) => {
          uni.hideLoading();
          if (this.pageIndex == 1 && res.errorCode == 10011) {
            uni.showToast({
              title: '暂无搜索结果',
              icon: 'none',
            });
          } else {
            uni.showToast({
              title: '加载完毕',
              icon: 'none',
            });
          }
          this.isLoadAll = true;
          this.pageIndex = 1;
        })
      },

      /**
       * 获取服务器搜索页面的默认数据
       */
      _getSearchServerConfig() {
        getServerConfigValue({
          field: 'mp_search_input_tips,mp_default_search_keyword'
        }).then((res) => {
          const {
            data
          } = res
          this.inputTips = data.hasOwnProperty('mp_search_input_tips') ? data.mp_search_input_tips : ''
          this.defaultKeyword = (data.hasOwnProperty('mp_default_search_keyword') && data.mp_default_search_keyword !=
              '') ?
            data.mp_default_search_keyword.split(',') : []
        })
      },

      // 从缓存中读取历史搜索的值
      _getRecentSearchKeyword() {
        let keyword_cache_array = uni.getStorageSync(this.keyword_cache_key);

        this.recentSearchKeywords = keyword_cache_array || [];

      },

      /**
       * 往缓存中添加历史搜索的值
       */
      _addRecentSearchKeyword() {

        const keyword_cache_array = this.recentSearchKeywords;
        const value = this.inputKeyword;

        // 如果搜索关键词为空则不进行添加操作
        if (value == null || value == '') {
          return keyword_cache_array;
        }

        // 判断当前长度是否大于等于最大长度
        if (keyword_cache_array.length >= this.keyword_cache_array_max_len) {
          // 删除最后一个
          keyword_cache_array.pop();
        }

        // 判断当前的历史搜索值中是否存在相同的值，如果存在则不进行添加
        if (keyword_cache_array.indexOf(value) === -1) {
          // 将新值添加到第一个位置
          keyword_cache_array.unshift(value);
        }

        // 将新数组保存到缓存中
        uni.setStorageSync(this.keyword_cache_key, keyword_cache_array);

        this.recentSearchKeywords = keyword_cache_array
      },

      // 设置搜索结果容器的高度
      _resizeResultView() {

        // 获取整个页面的高度
        uni.getSystemInfo({
          success: (res) => {
            const windowHeight = res.windowHeight;
            // 获取搜索结果所在view的信息
            uni.createSelectorQuery().select('#search-result').boundingClientRect((rect) => {

              this.resultViewHeight = windowHeight - rect.top

            }).exec();
          },
        });
      }
    }
  }
</script>

<style scoped>
  .search-search {
    background: rgba(0, 0, 0, 0.06);
  }

  .cu-avatar.df {
    width: 90rpx;
    height: 90rpx;
    font-size: 2em;
  }

  .padding-img-butt {
    margin-left: -30rpx
  }
</style>
