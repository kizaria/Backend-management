<template>
  <div class="top-category">
    <div class="container m-top-20">
      <div class="ref-goods">
        <div class="head">
          <h3>信息通知</h3>
        </div>


        <el-timeline style="max-width: 700px;">


          <el-timeline-item v-for="item in notice" :timestamp="item.time" placement="top">
            <el-card>
              <p>{{ item.content }}</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, nextTick, onBeforeMount } from 'vue'
import { deleteApi, getGoodsListApi, setIndexApi, upanddownApi, editGoodsApi, categoryApi } from '@/api/goods/index'
import useInstance from '@/hooks/useInstance'
import useDialog from '@/hooks/useDialog'
import SysDialog from '@/components/SysDialog.vue'
import UploadImage from '@/components/UploadImage.vue'
import { UserStore } from '@/stores/user'
import { NoticeStore } from '@/stores/notice/index';
import { friendApi } from '@/api/friend/index'
import { getMessage } from '@/api/chat/index'
import { getNoticeApi } from '@/api/notice/index'
//获取pinia
let user = UserStore()
let NStore = NoticeStore()
let notice = ref([])

let msg = ref()

let client = ref()

//查询数据库
let noticesql = async () => {
  let res = await getNoticeApi({ userId: user.getUserId })
  if (res.code == 200) {
    notice.value = res.data
    console.log(notice.value)
  }
}

onMounted(() => {
  noticesql()
  client = new WebSocket('ws://localhost:8090/imserverOrder/')

  client.onopen = () => {
    console.log('web open')
  }

  client.onclose = () => {
    console.log('web close')
  }

  client.onmessage = (msg) => {
    if (msg.data) {
      let json = JSON.parse(msg.data)
      if (json.userId===user.getUserId) {
          notice.value.push(json)
          NStore.setisNotice(true)
      }
      console.log("NStore.setisNotice(true)")

    }
  }
})

onBeforeMount(() => {
  if (client.value) {
    client.close()
  }
})


</script>

<style scoped lang="scss">
.top-category {
  .container {
    width: 600px;
  }

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
      padding: 0 40px 30px;
    }
  }

  .bread-container {
    padding: 25px 0;
  }
}
</style>
