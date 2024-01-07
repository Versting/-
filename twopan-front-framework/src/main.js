// 导入Vue和App.vue文件
import Vue from 'vue'
import App from './App.vue'
import VueCookies from "vue-cookies";



// 导入Vue Router和Element UI
import axios from 'axios';
import router from '@/router/index.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'




// axios.defaults.baseURL = 'http://192.168.33.104:8000/'
// axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=utf-8';
// Vue.prototype.$axios = axios

axios.defaults.baseURL = 'http://192.168.8.53:8000/'

axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=utf-8';
Vue.prototype.$axios = axios



// import '@/assets/css/global.css'
// 在 main.js 或类似的地方配置 Axios

//引入图标
import'./assets/icon/iconfont.css'
import'./assets/base.scss'


// 将 Axios 添加到 Vue 实例中
// Vue.prototype.$axios = axios;
// 设置 Axios 全局默认配置，例如设置 baseURL

// axios.defaults.baseURL = 'http://192.168.216.104:8000';




// 使用Element UI插件
Vue.use(ElementUI);
Vue.use(VueCookies);


Vue.config.productionTip = false

// 创建Vue实例
new Vue({
  // 注册Vue Router
  router,
  // 渲染根组件
  render: h => h(App)
  // 挂载到id为'app'的DOM元素上
}).$mount('#app')




