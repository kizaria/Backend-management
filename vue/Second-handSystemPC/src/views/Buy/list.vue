<!-- 求购信息列表 -->
<template>
  <div v-for="help in helpList" :key="help.id" class="top-category">
    <div class="container m-top-20">
      <div class="ref-goods">
        <div class="head">
          <h3>求购信息</h3>
        </div>
        <div class="body">
          <router-link :to="`/help/${help.id}`" v-animate="['slideUpBigIn']" :key="help.id">
            <div style="display: flex;box-sizing: border-box;">
              <!-- 用户头像 -->
              <img style="width: 50px; height: 50px; border-radius: 50%;" :src="help.picture">
              <div style="margin-left: 10px; flex: 1 1 0%;">
                <div style="font-weight:bolder;padding-bottom: 5px;"> {{ help.title }}

                  <div v-if="help.solved=='0'" style="float: right;">
                  未解决
                  </div>

                  <div v-else style="float: right;">
                    已解决
                  </div>

                </div>

                <div>
                  <span style="padding-right: 20px;">{{ help.nickName }}</span>
                  <span>{{ help.time }}</span>
                </div>
              </div>

            </div>


            <!-- 说说内容 -->
            <div style="padding-top: 20px;padding-left: 20px;" v-html="help.content">
            </div>

          </router-link>

        </div>
      </div>
    </div>
  </div>


</template>

<script setup>
import Breadcrumb from './Breadcrumb.vue'
import HomeBanner from '@/views/Home/components/HomeBanner.vue'
import { reactive, ref, onMounted, nextTick } from 'vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import { getGoodsHelpApi } from '@/api/help/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';

let helpList = ref([])

//查询求购信息
let getGoodsHelp = async () => {
  let res = await getGoodsHelpApi()
  if (res.code == 200) {
    helpList.value = res.data
    console.log(helpList.value)
    console.log(res.data)
  }
}

onMounted(() => {
  getGoodsHelp()
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