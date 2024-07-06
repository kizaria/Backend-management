<template>
  <div class="home-banner">
    <el-carousel height="500px">
      <el-carousel-item v-for="item in bannerList" :key="item.banId">
        <img @click="$router.push({path:`/detail/${item.goodsId}`}) " :src="item.images" alt="">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup>
import router from '@/router';
import { nextTick, onMounted, reactive, ref } from 'vue';
import {BannerGetGoods, addBannerApi,getBannerListApi,upanddownBannerApi} from '@/api/banner/index'


let bannerList = ref([])


//列表查询
let getBannerList=async()=>{
    let res=await BannerGetGoods()
    console.log(res)
    if(res&&res.code==200){
      bannerList.value = res.data
    }
}


onMounted(()=>{
    getBannerList()
    
})
</script>

<style scoped lang='scss'>
.home-banner {
  position: relative;
  z-index: 99;
  width: 1240px;
  height: 500px;
  left: 0;
  top: 0;
  z-index: 98;

  img {
    width: 100%;
    height: 500px;
  }
}
</style>