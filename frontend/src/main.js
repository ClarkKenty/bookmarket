// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VeLine from 'v-charts/lib/line.common'
import VeMap from 'v-charts/lib/map.common'
// 下面的依赖可以按照需求选择性加载
// The following dependencies can be selectively loaded on demand
import 'echarts/lib/component/markLine'
import 'echarts/lib/component/markPoint'
import 'echarts/lib/component/markArea'
import 'echarts/lib/component/visualMap'
import 'echarts/lib/component/dataZoom'
import 'echarts/lib/component/toolbox'
import 'echarts/lib/component/title'
import 'zrender/lib/svg/svg'
import 'v-charts/lib/style.css'
import VePie from 'v-charts/lib/Pie'
import router from './router'
import App from './App'

[VeLine, VePie, VeMap].forEach(comp => {
  Vue.component(comp.name, comp)
})

Vue.config.productionTip = false
// import 'materialize-css'
// import 'materialize-css/dist/css/materialize.css'

Vue.use(ElementUI)

// 设置反向代理，前端请求默认发送到 http://localhost:8443/api
var axios = require('axios')
axios.defaults.baseURL = 'http://darkcraft.top/api'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.user.username) {
      next()
    } else {
      next({
        path: 'login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
}
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
