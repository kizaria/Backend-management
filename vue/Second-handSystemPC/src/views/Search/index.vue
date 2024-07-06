<template>
    <Breadcrumb/>
    <div class="top-category">
      <div class="container m-top-20">
        <div class="ref-goods" >
        <div class="head">
            <h3>-搜索结果-</h3>
        </div>
        <div class="body">
            <GoodsItem v-for="goods in GoodsList" :goods="goods" :key="goods.categoryId" />
        </div>
      </div>
      
    </div>
    </div>


    <div v-for="help in helpList" :key="help.id" class="top-category">
      <div class="container m-top-20">
        <div class="ref-goods" >
        <div class="head">
            <h3>求购信息</h3>
        </div>
        <div class="body" >
          <router-link :to="`/help/${help.id}`"  v-animate="['slideUpBigIn']" 
        :key="help.id">
        <div style="display: flex;box-sizing: border-box;" >
          <!-- 用户头像 -->
          <img style="width: 50px; height: 50px; border-radius: 50%;"  :src="help.picture">
            <div style="margin-left: 10px; flex: 1 1 0%;">
                <div style="font-weight:bolder;padding-bottom: 5px;"> {{ help.title }}</div>
                <div>
            <span style="padding-right: 20px;">{{ help.nickName }}</span>
            <span >{{ help.time }}</span>
                </div>
            </div>
        </div>


        <!-- 说说内容 -->
        <div style="padding-top: 20px;padding-left: 20px;"  v-html="help.content">
        </div>

          </router-link>

        </div>
      </div>
    </div>
    </div>
</template>

<script setup>
import GoodsItem from '@/views/Home/components/GoodsItem.vue'
import { onMounted, reactive, ref,watch } from 'vue'
import { useRoute } from 'vue-router'
import router from '@/router';
import { getInfoApi } from '@/api/wxuser/index';
import { collectApi,hasCollectApi } from '@/api/collect/index';
import { getOrderByIdApi } from '@/api/order/index';
import { searchGoodsApi } from '@/api/goods/index';
import Breadcrumb from './Breadcrumb.vue'
import HomeBanner from '@/views/Home/components/HomeBanner.vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import {getGoodsHelpApi, searchHelpApi} from '@/api/help/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';

let CateList = ref([])
//获取地址栏数据
let route = useRoute()
let GoodsList = ref([])

//搜索结果
let getUsedList=async(goodsname)=>{
    let res = await searchGoodsApi({name:goodsname})
   if(res.code==200){
    GoodsList.value=res.data
  }
}

    // 监听query的变化
    watch(
      () => route.query,
      (newQuery, oldQuery) => {
        // 这里处理query变化的逻辑
        getUsedList(newQuery.name)
        getGoodsHelp(newQuery.name)
      },
      {
        deep: true, // 深度监听
      }
    );

let helpList=ref([])

//查询求购信息
let getGoodsHelp= async (name)=>{
  let res =await searchHelpApi({
    title:name
  })
  if(res.code==200){
    console.log(res)
    helpList.value=res.data

  }
}


//搜索结果
onMounted(()=>{
    getUsedList(route.query.name)
    getGoodsHelp(route.query.name)
})

</script>


<style scoped lang="scss">
.top-category {
  h3 {
    font-size: 28px;
    color: #666;
    font-weight: normal;
    text-align: center;
    line-height: 100px;
  }

  .sub-list {
    margin-top: 20px;
    background-color: #fff;

    ul {
      display: flex;
      padding: 0 32px;
      flex-wrap: wrap;

      li {
        width: 168px;
        height: 160px;


        a {
          text-align: center;
          display: block;
          font-size: 16px;

          img {
            width: 100px;
            height: 100px;
          }

          p {
            line-height: 40px;
            align-items: center;
          }

          &:hover {
            color: $xtxColor;
          }
        }
      }
    }
  }

  .ref-goods {
    background-color: #fff;
    margin-top: 20px;
    position: relative;

    .head {

      .xtx-more {
        position: absolute;
        top: 20px;
        right: 20px;
      }

      .tag {
        text-align: center;
        color: #999;
        font-size: 20px;
        position: relative;
        top: -20px;
      }
    }

    .body {
      flex-wrap: wrap;
      display: flex;
    //   justify-content: space-around;
      padding: 0 40px 30px;
    }
  }

  .bread-container {
    padding: 25px 0;
  }
}
</style>