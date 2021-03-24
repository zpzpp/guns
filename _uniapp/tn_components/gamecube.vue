<template>
	<view class="components-gamecube">
		<web-view src="https://www.tuniaokj.com/cube/index.html"></web-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
    onShareAppMessage() {
      return {
        title: 'web-view小游戏'
      }
    },
    onShareTimeline() {
      return {
        title: 'web-view小游戏'
      }
    },
		methods: {
			
		}
	}
</script>

<style scoped>

</style>
