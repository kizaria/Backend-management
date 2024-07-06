<template>
  <header class='app-header'>
    <div class="container">
      <h1 class="logo">
        <RouterLink to="/">小兔鲜</RouterLink>
      </h1>
      <ul class="app-header-nav">
        <li class="home">
          <RouterLink exact-active-class="active" to="/">首页</RouterLink>
        </li>
        <li> <RouterLink active-class="active" to="/commodity">闲置商品</RouterLink> </li>
        <li> <RouterLink active-class="active" to="/buy">求购专区</RouterLink> </li>
        <li> <RouterLink active-class="active" to="/announcement">网站公告</RouterLink> </li>


      </ul>
      <div class="search">
        <i class="iconfont icon-search"></i>
        <input type="text" v-model="search" placeholder="搜一搜" @keyup.enter="searchSum()">
      </div>
      <!-- 头部购物车 -->
      
    </div>
  </header>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import router from '@/router';
import {useRoute} from 'vue-router'
import { UserStore } from '@/stores/user/index';
import {getAddressApi} from '@/api/address/index'
import {getGoodsById} from '@/api/goods/index'
import {replaceOrderApi} from '@/api/order/index'
//搜索
let search=ref()

let searchSum=()=>{
  router.push({path:'/search',query: {name:search.value}})  
}
</script>

<style scoped lang='scss'>
.app-header {
  background: #fff;

  .container {
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200px;

    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      background: url('@/assets/images/logo.png') no-repeat center 18px / contain;
    }
  }

  .app-header-nav {
    width: 820px;
    display: flex;
    padding-left: 40px;
    position: relative;
    z-index: 998;
   
  
    li {
      margin-right: 30px;
      width: 72px;
      text-align: center;
      
  
      a {
        font-size: 16px;
        line-height: 32px;
        font-weight: 600;
        height: 32px;
        display: inline-block;
  
        &:hover {
          color: $xtxColor;
          border-bottom: 1px solid $xtxColor;
        }
      }
  
      .active {
        color: $xtxColor;
        border-bottom: 1px solid $xtxColor;
      }
    }
  }

  .search {
    width: 170px;
    height: 32px;
    position: relative;
    border-bottom: 1px solid #e7e7e7;
    line-height: 32px;

    .icon-search {
      font-size: 18px;
      margin-left: 5px;
    }

    input {
      width: 140px;
      padding-left: 5px;
      color: #666;
    }
  }

  .cart {
    width: 50px;

    .curr {
      height: 32px;
      line-height: 32px;
      text-align: center;
      position: relative;
      display: block;

      .icon-cart {
        font-size: 22px;
      }

      em {
        font-style: normal;
        position: absolute;
        right: 0;
        top: 0;
        padding: 1px 6px;
        line-height: 1;
        background: $helpColor;
        color: #fff;
        font-size: 12px;
        border-radius: 10px;
        font-family: Arial;
      }
    }
  }
}
</style>