<!-- 闲置商品页面 -->
<template>
  <div class="top-category">
    <div class="container m-top-20">

      <div class="sub-list">
        <h3>全部分类</h3>
        <ul>
          <li>
            <div @click="getUsedList()" style=" cursor: pointer;">
              <el-image style="width: 100px; height: 100px"
                src="https://pic.616pic.com/ys_bnew_img/00/18/44/8vNgb40WNO.jpg" :fit="fit" />
              <p style="padding-left: 20%;">全部</p>

            </div>

          </li>
          <li v-for="item in CateList" :key="item.categoryId">
            <div style=" cursor: pointer;" @click="getUsedList(item.categoryId, item.categoryName)">
              <el-image style="width: 100px; height: 100px" :src="item.images" :fit="fit" />
              <p style="padding-left: 20%;">{{ item.categoryName }}</p>
            </div>

            <!-- <RouterLink to="" @click="getUsedList(item.categoryId,item.categoryName)">
                    <img :src="item.images" />
                    <p>{{ item.categoryName }}</p>
                </RouterLink> -->
          </li>
        </ul>
      </div>

      <div class="ref-goods">
        <div class="head">
          <h3>-{{ CurrentCate }}-</h3>
        </div>
        <div class="body">
          <GoodsItem v-for="goods in CateGoodsList" :goods="goods" :key="goods.goodsId" />
        </div>
      </div>


    </div>
  </div>
</template>

<script setup>
import GoodsItem from '@/views/Home/components/GoodsItem.vue'

import { getGoodsProduct, getUsedListApi } from '@/api/goods/index'
import { getCateListApi } from '@/api/category/index'
import { onMounted } from 'vue';
import { ref, reactive } from 'vue'


let CateList = ref([])
//分类
let getCateList = async () => {
  let res = await getCateListApi()
  if (res.code == 200) {
    console.log(res)
    CateList.value = res.data
  }
}

//绑定的对象
let Parm = reactive({
  categoryId: "",
  keywords: ""
})
let CurrentCate = ref()
let CateGoodsList = ref([])
//根据商品分类获取商品
let getUsedList = async (categoryId, categoryName) => {
  console.log(categoryName)
  if (categoryName == null) {
    CurrentCate.value = "全部"
  } else {
    CurrentCate.value = categoryName
  }
  Parm.categoryId = categoryId;

  let res = await getUsedListApi(Parm)
  if (res.code == 200) {
    console.log(res)
    CateGoodsList.value = ''
    CateGoodsList.value = res.data
    console.log(CateGoodsList.value)

  }
}

onMounted(() => {
  getCateList()
  getUsedList()
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