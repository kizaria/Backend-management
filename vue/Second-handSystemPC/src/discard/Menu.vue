<template>
  <MenuLogo></MenuLogo>
  <el-menu :default-active="activeindex" unique-opened	router class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen" @close="handleClose"
    background-color="#304156">
   <menu-item :menuList="menuList"></menu-item>
  </el-menu>
</template>
   
<script setup lang="ts">
import { computed, ref } from 'vue'
import { reactive } from 'vue'
import MenuItem from '@/discard/MenuItem.vue'
import {useRoute} from 'vue-router'
import MenuLogo from '@/layout/MenuLogo.vue'
import {CollapseStore} from '@/stores/Collapse/Collapse'

let store=CollapseStore();
const isCollapse=computed(()=>{
  console.log(store.collapse)
  console.log(store.getCollapse)
  return store.getCollapse
 
})

//获取当前路由
let route=useRoute();
//当前激活的导航菜单
let activeindex=computed(()=>{
  let {path}=route;
  return path;
})

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

//菜单数据
let menuList = reactive([
  {
    path: "/dashboard",
    Comment: "Layout",
    name: "dashboard",
    meta: {
      title: "首页",
      icon: "HomeFilled",
      roles: ["sys:dashboard"]
    }
  },

  {
    path: "/system",
    Comment: "Layout",
    name: "system",
    meta: {
      title: "系统管理",
      icon: "Setting",
      roles: ["sys:manage"]
    },
    children: [
      {
        path: "/adminUser",
        Comment: "/system/AdminUser",
        name: "adminUser",
        meta: {
          title: "管理员管理",
          icon: "Avatar",
          roles: ["sys:adminUser"]
        }
      },
      {
        path: "/userList",
        Comment: "/system/UserList",
        name: "userList",
        meta: {
          title: "用户管理",
          icon: "User",
          roles: ["sys:userList"]
        }
      },
      {
        path: "/menuList",
        Comment: "/system/MenuList",
        name: "menuList",
        meta: {
          title: "菜单管理",
          icon: "Operation",
          roles: ["sys:menu"]
        }
      },
    ]
  },
  {
    path: "/goodsRoot",
    Comment: "Layout",
    name: "goodsRoot",
    meta: {
      title: "商品管理",
      icon: "Shop",
      roles: ["sys:goodsRoot"]
    },
    children: [
      {
        path: "/goodsType",
        Comment: "/goods/GoodsType",
        name: "goodsType",
        meta: {
          title: "商品分类",
          icon: "Setting",
          roles: ["sys:goodsType"]
        }
      },
      {
        path: "/unusedList",
        Comment: "/goods/UnusedList",
        name: "unusedList",
        meta: {
          title: "闲置商品",
          icon: "GoodsFilled",
          roles: ["sys:unusedList "]
        }
      },
      {
        path: "/buyList",
        Comment: "/goods/BuyList",
        name: "buyList",
        meta: {
          title: "求购商品",
          icon: "GoodsFilled",
          roles: ["sys:buyList"]
        }
      },
    ]
  },
  {
    path: "/order",
    Comment: "Layout",
    name: "order",
    meta: {
      title: "订单管理",
      icon: "CreditCard",
      roles: ["sys:order"]
    },
    children: [
      {
        path: "/unusedOrder",
        Comment: "/order/UnusedOrder",
        name: "unusedOrder",
        meta: {
          title: "闲置订单",
          icon: "Suitcase",
          roles: ["sys:unusedOrder"]
        }
      },
      {
        path: "/buyOrder",
        Comment: "/order/BuyOrder",
        name: "buyOrder",
        meta: {
          title: "求购订单",
          icon: "SuitcaseLine",
          roles: ["sys:buyOrder"]
        }
      },
    ]
  },
  {
    path: "/comment",
    Comment: "Layout",
    name: "comment",
    meta: {
      title: "评论管理",
      icon: "ChatSquare",
      roles: ["sys:comment"]
    },
    children: [
      {
        path: "/commentList",
        Comment: "/comment/CommentList",
        name: "commentList",
        meta: {
          title: "评论列表",
          icon: "ChatRound",
          roles: ["sys:commentList"]
        }
      },
    ]
  }

])
</script>

<style scoped>
/* :: v-deep使用场景:
当我们需要覆盖element-ui中的样式时只能通过深度作用选择器 */
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu {
  /* 去掉右边框 */
  border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
  color: #f4f4f5 !important;
  /* !important让浏览器首选执行这个语句 */
}

:deep(.el-menu .el-menu-item) {
  color: #bfcbd9;
}

/* 菜单点击文字的颜色 */
:deep(.el-menu-item.is-active) {
  color: #f07810 !important;
}

/* 当前打开菜单的所有子菜单的颜色 */
:deep(.is-opened .el-menu-item) {
  background-color: #1f2d3d !important;
}

/* 鼠标移动菜单的颜色 */
:deep(.el-menu-item:hover) {
  background-color: #001528 !important;
}
</style>