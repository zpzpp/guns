<template>
  <view class="tn-login">
    <view :class="['cu-modal', show ? 'show' : '']">
      <view class="cu-dialog">
        <view class="cu-bar bg-white justify-end solid-bottom">
          <view class='content text-bold text-xl'>{{tips.title}}</view>
        </view>
        <view class='padding-sm bg-white'>
          <form>
            <view class="cu-list menu">
              <view class="cu-item">
                <view class='content padding-tb-sm'>
                  <view class="text-left text-lg">
                    {{tips.msg}}
                  </view>
                  <view class='text-gray text-left'>
                    <text class='cuIcon-infofill'></text> 获得您的公开信息（昵称、头像等）
                  </view>
                </view>
              </view>
            </view>

            <view class="margin-top">
              <view class="cu-bar btn-group">
                <button class='cu-btn bg-grey shadow-blur round' @click="closeLoginModal">取 消</button>
                <button class='cu-btn bg-gradual-index shadow-blur round' open-type="getUserInfo" @getuserinfo="getUserInfo">允
                  许</button>
              </view>
            </view>
          </form>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    name: 'TnWxLogin',
    props: {
      tips: {
        type: Object,
        default: () => {
          return {
            title: '该操作需要登陆',
            msg: '该操作将获取以下授权为了更好的服务：'
          }
        }
      },
      show: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {

      }
    },
    methods: {
      closeLoginModal() {
        this.$emit('closeLoginModal')
      },
      getUserInfo() {
        this.$emit('getUserInfo')
      }
    }
  }
</script>

<style scoped>

</style>
