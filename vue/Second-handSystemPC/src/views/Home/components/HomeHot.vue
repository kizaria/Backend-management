<template>
    <HomePanel title="收藏推荐" sub-title="新鲜出炉 品质靠谱">
      <template #main>
        <ul class="goods-list">
          <li v-for="item in HotList" :key="item.goodsId">
            <RouterLink :to="`/detail/${item.goodsId}`">
              <img :src="item.image" alt="" />
              <p class="goodsName">{{ item.goodsName }}</p>
              <p class="price">&yen;{{ item.goodsPrice }}</p>
            </RouterLink>
          </li>
        </ul>
      </template>
    </HomePanel>
  </template>

<script setup>
import HomePanel from './HomePanel.vue'
import { getGoodsHot } from '@/api/collect/index'
import { onMounted } from 'vue';
import { ref } from 'vue'
let HotList = ref([])
let getGoodsHotList = async () => {
  let res = await getGoodsHot()
  console.log(res)
  HotList.value = res.data
}

onMounted(()=>{
  getGoodsHotList()
})
</script>

<style scoped lang='scss'>
.goods-list {
  display: flex;
  justify-content: space-between;
  height: 406px;

  li {
    width: 306px;
    height: 406px;

    background: #f0f9f4;
    transition: all .5s;

    &:hover {
      transform: translate3d(0, -3px, 0);
      box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
    }

    img {
      width: 306px;
      height: 306px;
    }

    p {
      font-size: 22px;
      padding-top: 12px;
      text-align: center;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
    }

    .price {
      color: $priceColor;
    }
  }
}
</style>