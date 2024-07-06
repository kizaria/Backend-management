<script setup>
  import { computed, ref } from 'vue'
  import { reactive } from 'vue'
  import {useRoute} from 'vue-router'

  //获取当前路由
  let route=useRoute();
  //当前激活的导航菜单
  let activeindex=computed(()=>{
    let {path}=route;
    return path;
  })
</script>

<template>
  <div class="container">
    <div class="xtx-member-aside">
      <div class="user-manage">
        <h4>我的账户</h4>
        
        <div class="links">
          <!-- <RouterLink to="/member/user">个人中心</RouterLink> -->
          <el-row>

    <el-col :span="24">
      <el-menu
    router 
    :default-active="activeindex"
    class="el-menu-vertical-demo"
    :collapse="isCollapse"
    active-text-color="#27ba9b"
    >

        <el-sub-menu index="1">
          <template #title>
            <span>个人中心</span>
          </template>
            <!-- <el-menu-item index="/member">个人中心</el-menu-item> -->
            <el-menu-item index="/member/info">修改个人</el-menu-item>
            <el-menu-item index="/member/password">修改密码</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <span>我的闲置</span>
          </template>
          <el-menu-item index="/unused/release">发布闲置</el-menu-item>
          <el-menu-item index="/unused">我的闲置</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="3">
          <template #title>
            <span>我的求购</span>
          </template>
          <el-menu-item index="/myrequest/request">发布求购</el-menu-item>
          <el-menu-item index="/myrequest">我的求购</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/collection">我的收藏</el-menu-item>
        <el-menu-item index="/report">我的举报</el-menu-item>

        <el-sub-menu index="4">
          <template #title>
            <span>我的订单</span>
          </template>
          <el-menu-item index="/myorder">我的订单</el-menu-item>
          <el-menu-item index="/unusedorder">闲置订单</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/chat">私信</el-menu-item>


        <el-menu-item index="/address">我的地址</el-menu-item>



      </el-menu>
    </el-col>
          </el-row>
        </div>
        
      </div>
    </div>
    <div class="article">
      <!-- 三级路由的挂载点 -->
      <RouterView />
    </div>
  </div>
</template>

<style scoped lang="scss">
  // 子目录被激活后的菜单背景色

.el-menu {
      border-right: 0;
}
.container {
  display: flex;
  padding-top: 20px;
  width: 1400PX;

  .xtx-member-aside {
    // width: 500px;
    margin-right: 20px;
    border-radius: 2px;
    background-color: #fff;

    .user-manage {
      background-color: #fff;

      h4 {
        font-size: 18px;
        font-weight: 400;
        padding: 20px 30px 5px;
        border-top: 1px solid #f6f6f6;
      }

      .links {
        padding: 0 52px 10px;
      }

      a {
        display: block;
        line-height: 1;
        padding: 15px 0;
        font-size: 14px;
        color: #666;
        position: relative;

        &:hover {
          color: $xtxColor;
        }

        &.active,
        &.router-link-exact-active {
          color: $xtxColor;

          &:before {
            display: block;
          }
        }

        &:before {
          content: '';
          display: none;
          width: 6px;
          height: 6px;
          border-radius: 50%;
          position: absolute;
          top: 19px;
          left: -16px;
          background-color: $xtxColor;
        }
      }
    }
  }

  .article {
    width: 100%;
    background-color: #fff;
  }
}
</style>