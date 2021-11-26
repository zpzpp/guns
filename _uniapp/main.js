import Vue from 'vue'
import App from './App'
import TnCustom from './components/TnCustom/TnCustom.vue'
import TnWxLogin from './components/TnWxLogin/TnWxLogin.vue'
import { getDataSet, getComponentInfo } from './utils/util.js'
// 注册全局组件
import MescrollBody from "@/uni_modules/mescroll-uni/components/mescroll-body/mescroll-body.vue"
import MescrollUni from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-uni.vue"

Vue.config.productionTip = false

App.mpType = 'app'

Vue.component('tn-custom', TnCustom)
Vue.component('tn-wx-login', TnWxLogin)
Vue.component('mescroll-body', MescrollBody)
Vue.component('mescroll-uni', MescrollUni)

Vue.prototype.$getDataSet = getDataSet
Vue.prototype.$getComponentInfo = getComponentInfo

const app = new Vue({
    ...App
})
app.$mount()
