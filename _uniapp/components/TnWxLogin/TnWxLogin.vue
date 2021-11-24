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
									<view class="cu-form-group margin-top text-left">
										<view class="title">姓名</view>
										<input placeholder="请输入真实姓名" v-model="name" maxlength="10" name="name"></input>
									</view>
									<view class="cu-form-group text-left">
										<view class="title">部门</view>
										<input placeholder="请输入所在部门" v-model="dept" maxlength="20" name="dept"></input>
									</view>
									<view class='text-gray text-left'>
										<text class='cuIcon-infofill'></text> 提交后请等待管理员审核
									</view>
								</view>
							</view>
						</view>

						<view class="margin-top">
							<view class="cu-bar btn-group">
								<button class='cu-btn bg-grey shadow-blur round' @click="closeLoginModal">取 消</button>
								<button v-if="canUseGetUserProfile" class="cu-btn bg-gradual-index shadow-blur round"
									type="primary" @click="getUserInfo_">授权</button>
								<button v-else class="cu-btn bg-gradual-index shadow-blur round" type="primary"
									open-type="getUserInfo" @getuserinfo="getUserInfo">授权</button>
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
				canUseGetUserProfile: false, //判断api是否存在
				name: '', //判断api是否存在
				dept: '', //判断api是否存在
			};
		},
		methods: {
			closeLoginModal() {
				this.$emit('closeLoginModal')
			},
			init() {
				debugger
				this.name = ''
				this.dept = ''
			},
			getUserInfo_() {
				if(!this.name){
					uni.showToast({
					  title: '请输入真实姓名',
					  icon: 'none'
					});
					return
				}
				if(!this.dept){
					uni.showToast({
					  title: '请输入所在部门',
					  icon: 'none'
					});
					return
				}
				this.$emit('getUserInfo', {
					'name': this.name,
					'dept': this.dept
				})

			}
		},
		mounted() {
			debugger
			if (uni.getUserProfile) {
				this.canUseGetUserProfile = true;
			}
		}
	}
</script>

<style scoped>

</style>
