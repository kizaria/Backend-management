import { createRouter, createWebHistory } from 'vue-router'

import Login from '@/views/Login/index.vue'
import Layout from '@/views/Layout/index.vue'
import Home from '@/views/Home/index.vue'
import Announcement from '@/views/Announcement/index.vue'
import Buy from '@/views/Buy/index.vue'
import Commodity from '@/views/Commodity/index.vue'
import Detail from '@/views/Detail/index.vue'
import Checkout from '@/views/Checkout/index.vue'
import Member from '@/views/Member/index.vue'
import userInfoIndex from '@/views/Member/userInfo/index.vue'
import info from '@/views/Member/userInfo/info.vue'
import password from '@/views/Member/userInfo/password.vue'
import myWantToBuyIndex from '@/views/Member/myWantToBuy/index.vue'
import myWantToBuyRelease from '@/views/Member/myWantToBuy/release.vue'
import Chat from '@/views/Member/chat/index.vue'
import HelpDetail from '@/views/Buy/HelpDetail.vue'
import unusedIndex from '@/views/Member/unused/index.vue'
import unusedRelease from '@/views/Member/unused/release.vue'
import collection from '@/views/Member/collection/index.vue'
import Pay from '@/views/Pay/index.vue'
import Search from '@/views/Search/index.vue'
import PayBack from '@/views/Pay/PayBack.vue'
import report from '@/views/Member/report/index.vue'
import myOrder from '@/views/Member/order/myOrder.vue'
import unusedOrder from '@/views/Member/order/unusedOrder.vue'
import address from '@/views/Member/address/index.vue'



let router = createRouter({
  history: createWebHistory(),
   // path和component对应关系的位置
   routes: [
    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '',
          component: Home,
          
        },
        {
          path: '/commodity',
          component: Commodity
        },
        {
          path: '/buy',
          component: Buy
        },
        {

          path: '/help/:id',
          component: HelpDetail
        },
        {
          path: '/announcement',
          component: Announcement
        },
        {
          path: '/detail/:id',
          component: Detail
        },
        {
          path: '/checkout',
          component: Checkout
        },
        {
          path: '/pay',
          component: Pay
        },
        {
          path: '/payback',
          component: PayBack
        },
        {
          path: '/search',
          component: Search
        },
        {
          path: '/member',
          component: Member,
          children: [
            {
              path: '',
              component: info
            },
            {
              path: '/member/info',
              component: info
            },
            {
              path: '/member/password',
              component: password
            },
            {
              path: '/myrequest',
              component: myWantToBuyIndex
            },
            {
              path: '/myrequest/request',
              component: myWantToBuyRelease
            },
            {
              path: '/chat',
              component: Chat
            },
            {
              path: '/unused',
              component: unusedIndex
            },
            {
              path: '/unused/release',
              component: unusedRelease
            },
            {
              path: '/collection',
              component: collection
            },
            {
              path: '/report',
              component:report

            },
            {
              path: '/myorder',
              component:myOrder
            },
            {
              path: '/unusedorder',
              component:unusedOrder
            },
            {
              path: '/address',
              component:address
            },
          ],
        }
      ]
    },
    {
      path: '/login',
      component: Login
    },
    
  ] , // 路由滚动行为定制
  scrollBehavior () {
    return {
      top: 0
    }
  }
})

export default router