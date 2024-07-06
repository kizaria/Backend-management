import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/Index.vue'
import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs'

const router = createRouter({
  history: createWebHistory(),
  routes: [

    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      children: [
        {
          path: '/dashboard',
          component: () => import('@/layout/Bashboard.vue'),
          name: 'dashboard',
          meta: {
            title: '首页',
            icon: ''
          }
        }
      ]
    },
    {
      path: "/system",
      component: Layout,
      name: "system",
      meta: {
        title: "系统管理",
        icon: "Setting",
        roles: ["sys:manage"]
      },
      children: [
        {
          path: "/adminUser",
          component: () => import('@/views/system/AdminUser.vue'),
          name: "adminUser",
          meta: {
            title: "管理员管理",
            icon: "Setting",
            roles: ["sys:adminUser"]
          }
        },
        {
          path: "/userList",
          component: () => import('@/views/system/UserList.vue'),
          name: "userList",
          meta: {
            title: "用户管理",
            icon: "",
            roles: ["sys:userList"]
          }
        },
        {
          path: "/menuList",
          component: () => import('@/views/system/MenuList.vue'),
          name: "menuList",
          meta: {
            title: "菜单管理",
            icon: "",
            roles: ["sys:menu"]
          }
        },
      ]
    },
    {
      path: "/goodsRoot",
      component: Layout,
      name: "goodsRoot",
      meta: {
        title: "商品管理",
        icon: "",
        roles: ["sys:goodsRoot"]
      },
      children: [
        {
          path: "/goodsType",
          component: () => import('@/views/goods/GoodsType.vue'),
          name: "goodsType",
          meta: {
            title: "商品分类",
            icon: "",
            roles: ["sys:goodsType"]
          }
        },
        {
          path: "/goodsList",
          component: () => import('@/views/goods/GoodsList.vue'),
          name: "goodsList",
          meta: {
            title: "闲置商品",
            icon: "",
            roles: ["sys:unusedList "]
          }
        },
        {
          path: "/buyList",
          component: () => import('@/views/goods/BuyList.vue'),
          name: "buyList",
          meta: {
            title: "求购商品",
            icon: "",
            roles: ["sys:buyList"]
          }
        },
      ]
    },
    {
      path: "/order",
      component: Layout,
      name: "order",
      meta: {
        title: "订单管理",
        icon: "",
        roles: ["sys:order"]
      },
      children: [
        {
          path: "/unusedOrder",
          component: () => import('@/views/order/UnusedOrder.vue'),
          name: "unusedOrder",
          meta: {
            title: "闲置订单",
            icon: "",
            roles: ["sys:unusedOrder"]
          }
        },
        {
          path: "/buyOrder",
          component: () => import('@/views/order/BuyOrder.vue'),
          name: "buyOrder",
          meta: {
            title: "求购订单",
            icon: "",
            roles: ["sys:buyOrder"]
          }
        },
      ]
    },
    {
      path: "/comment",
      component: Layout,
      name: "comment",
      meta: {
        title: "评论管理",
        icon: "",
        roles: ["sys:comment"]
      },
      children: [
        {
          path: "/commentList",
          component: () => import('@/views/comment/CommentList.vue'),
          name: "commentList",
          meta: {
            title: "评论列表",
            icon: "",
            roles: ["sys:commentList"]
          }
        },
      ]
    }
  ]
})

export default router
