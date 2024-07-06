<template>
  <div class="container">
    <div class="xtx-member-aside">
      <div class="user-manage">
        <div style="width: 300px;margin-bottom: 15px;margin-top: 15px;" v-for="item in users"
          @click="selectToUser(item)">


          <div style="display: flex;">
          <img style="width: 50px; height: 50px; border-radius: 50%;margin-left:   5px;" :src="item.friendavatar" />

            <span style="margin-left: 10px;font-size:larger ;margin-top: 10px;">{{ item.friendname }}</span>
            <div style="display: flex;" v-for="read in unRead">
              <div v-if="item.friendname == read.fromuser">
                <i style="width:20px;height:20px;border-radius:50%;background-color:red;display: block;
                margin-top: 13px;margin-left: 170PX;
                "></i>
              </div>
            </div>
          </div>

          <hr>
        </div>
      </div>
    </div>


    <div class="article" ref="scrollDiv">
      <div style="font-size:x-large;text-align: center;">{{ toUser }} </div>
      <div v-if="toUser!=null"><hr></div>
      <div style="width: 750px;" v-for="item in messages" :key="item.id">
        <!-- 右边聊天 -->
        <div style=" display: flex;flex-direction: row-reverse;margin-bottom: 15px;margin-right: 15px;"
          v-if="item.fromuser == fromUser">
          <img style="width: 50px; height: 50px; border-radius: 50%;margin-left:   5px;" :src="item.fromavater" />
          <span style="padding-top: 15px;padding-right: 15px;padding-left: 15px;
             border: 1px solid #000;border-radius: 15px;">{{ item.content }}</span>


        </div>
        <!-- 左边聊天 -->
        <div style=" display: flex;margin-bottom: 15px;margin-right: 15px;" v-else>
          <img style="width: 50px; height: 50px; border-radius: 50%;margin-left:   5px;" :src="item.fromavater" />
          <span style="padding-top: 15px;padding-left:  5px;margin-left: 15px;
             border: 1px solid #000;border-radius: 15px;  padding-right: 15px;">{{ item.content }}</span>
        </div>
        <!-- <div style="margin-bottom: 15px;margin-right: 15px;" v-else>
              <img  style="width: 50px; height: 50px; border-radius: 50%;margin-left:   5px;"   :src="item.fromavater"/>
              <span style="padding-top: 15px;padding-left: 15px;background-color: aqua;">{{ item.content }}</span>
            </div> -->
      </div>

      <div  v-if="toUser!=null" style="position: absolute;bottom: 0;"><hr></div>
      <!-- 输入区域 -->
      <div id="sr" v-if="toUser" style="margin-left: 15px;margin-right: 15px;margin-bottom: 15px;">
        <el-input style="width: 650px;" v-model="msg"></el-input> <el-button @click="send()">发送</el-button>
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
import { friendApi, readApi } from '@/api/friend/index'
import { getMessage } from '@/api/chat/index'
//获取pinia
let userpoinia = UserStore()

let user = ref({})
let permission = ref([])
let emojis = ref([])
let messages = ref([])
let users = ref({})
//当前发送人
let fromUser = ref()
let fromAvatar = ref()

let toUser = ref()
let toAvatar = ref()
let unRead = ref({})

let msg = ref()

let client = ref()

//查询好友
let friend = async () => {
  let res = await friendApi({ userId: userpoinia.getUserId })
  if (res.code == 200) {
    console.log(res.data)
    users.value = res.data
  }
}

//查询好友未读
let friendRead = async () => {
  let res = await readApi({ userId: userpoinia.getUserId })
  if (res.code == 200) {
    console.log(res.data)
    unRead.value = res.data
  }
}
//加载聊天数据
let load = async () => {
  let res = await getMessage({
    fromUser: fromUser.value,
    toUser: toUser.value
  })
  if (res.code == 200) {
    console.log(res.data)
    messages.value = res.data
  }
  friendRead()
}

let selectToUser = (item) => {
  toUser.value = item.friendname
  toAvatar.value = item.friendavatar
  //查询聊天记录
  load()
}
let send = () => {
  if (client) {
    client.send(JSON.stringify(getMessaget()))
  }
  msg.value=''
  setTimeout(() => {
    //需要延迟的代码 :3秒后延迟
    boxToBottom()
    //延迟时间：3秒
  }, 1)
  // boxToBottom()

}


const boxToBottom = () => {
  // 滚动到底部
  let element = document.getElementsByClassName('article')[1]
  console.log(element)
  console.log(element.scrollTop)
  console.log(element.scrollHeight)
  element.scrollTop = element.scrollHeight + 5000;

}


let getMessaget = () => {
  return {
    fromuser: fromUser.value,
    fromavater: fromAvatar.value,
    touser: toUser.value,
    toavater: toAvatar.value,
    content: msg.value
  }
}
onMounted(() => {
  fromUser.value = userpoinia.getNickName
  fromAvatar.value = userpoinia.getavatar
  friend()
  friendRead()

  client = new WebSocket('ws://localhost:8090/imserverSingle/')

  client.onopen = () => {
    console.log('web open')
  }

  client.onclose = () => {
    console.log('web close')
  }

  client.onmessage = (msg) => {
    if (msg.data) {
      let json = JSON.parse(msg.data)
      if (json.content && (json.fromuser === fromUser.value && json.touser === toUser.value)
        || (json.fromuser === toUser.value && json.touser === fromUser.value)) {
        messages.value.push(json)
        boxToBottom()
      }

      if (toUser.value == json.fromuser) {
        load()
        friendRead()//清空当前人的聊天记录
      } else {
        friendRead()
      }
    }

  }


  //加载聊天数据
  load()

})

onBeforeMount(() => {
  if (client.value) {
    client.close()
  }
})


</script>

<style scoped lang="scss">
// 子目录被激活后的菜单背景色

.el-menu {
  border-right: 0;
}

.container {
  display: flex;

  width: 1100PX;


  .xtx-member-aside {
    border-width: 1px;
    /* 设置边框宽度 */
    border-style: double;
    /* 设置边框样式 */
    border-color: #000;
    /* 设置边框颜色 */
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
    border-width: 1px;
    /* 设置边框宽度 */
    border-style: double;
    /* 设置边框样式 */
    border-color: #000;
    /* 设置边框颜色 */
    width: 100%;
    height: 500px;
    /* 设置div的固定高度 */
    overflow-y: auto;
    /* 当内容超出固定高度时显示垂直滚动条 */
    background-color: #fff;
  }
}
</style>