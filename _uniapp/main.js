import Vue from 'vue'
import App from './App'
import TnCustom from './components/TnCustom/TnCustom.vue'
import TnWxLogin from './components/TnWxLogin/TnWxLogin.vue'
import { getDataSet, getComponentInfo } from './utils/util.js'

Vue.config.productionTip = false

App.mpType = 'app'

Vue.component('tn-custom', TnCustom)
Vue.component('tn-wx-login', TnWxLogin)
Vue.prototype.$getDataSet = getDataSet
Vue.prototype.$getComponentInfo = getComponentInfo

const app = new Vue({
    ...App
})
app.$mount()
