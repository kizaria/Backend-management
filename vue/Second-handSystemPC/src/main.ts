
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' //引入持久化插件
import zhCn from 'element-plus/es/locale/lang/zh-cn'
//引入初始化样式文件
import '@/styles/common.scss'
import myconfirm from './utils/myconfirm'

import App from './App.vue'
import router from './router'

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(pinia)
app.use(router)
app.use(ElementPlus, {
  locale: zhCn,
})

app.mount('#app')

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
//全局挂载 $符号单纯为了避免命名重复
app.config.globalProperties.$myconfirm=myconfirm