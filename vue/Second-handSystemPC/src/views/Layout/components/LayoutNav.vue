<template>
  <!-- 而nav标签则更具有语义化，它用于定义一个页面的主要导航部分 -->
  <nav class="app-topnav">
    <div class="container">
      <ul>



        <template v-if="store.getUserId">

          <div v-if="!NStore.getisNotice" style="width: 2%;" @click="dialog()">
            <img style="cursor: pointer;" src="@/assets/images/通知1.png">
          </div>

          <div  v-if="NStore.getisNotice" style="width: 2%;" @click="dialog()">
            <img style="cursor: pointer;" src="@/assets/images/通知2.png">
          </div>


          <li @click="$router.push('/member')"><a href="javascript:;"><i
                class="iconfont icon-user"></i>{{ store.getNickName }}</a></li>
          <li>
            <el-popconfirm @confirm="confirm" title="确认退出吗?" confirm-button-text="确认" cancel-button-text="取消">
              <template #reference>
                <a href="javascript:;">退出登录</a>
              </template>
            </el-popconfirm>
          </li>
        </template>
        <template v-else>
          <li><a href="javascript:;" @click="$router.push('/login')">请先登录</a></li>
          <li><a href="javascript:;">帮助中心</a></li>
          <li><a href="javascript:;">关于我们</a></li>
        </template>
      </ul>
    </div>
  </nav>


  <el-dialog v-model="dialogTableVisible" title="编辑商品" width="800">
    <notice />

  </el-dialog>
</template>
<script setup>
import { UserStore } from '@/stores/user/index';
import { NoticeStore } from '@/stores/notice/index';
import router from '@/router';
import { ref, watch,onMounted } from 'vue'
import notice from './notice.vue';
import { getReadApi } from '@/api/notice/index'

//获取pinia
let store = UserStore()
let NStore = NoticeStore()


//查询是否有未读通知
let getRead=async()=>{
  let res=await getReadApi({userId:store.getUserId})
  if(res.code==200){
    console.log("notice is ?")
    console.log(res.data)
    if(res.data=='1'){
      NStore.setisNotice(true)
    }else{
      NStore.setisNotice(false)
    }
  }
}

const confirm = () => {
  //清空缓存
  localStorage.clear()
  //跳转登录
  window.location.href="/login"
  // store.setinfo()



}

let dialogTableVisible = ref(false)

let dialog = () => {
  dialogTableVisible.value = !dialogTableVisible.value
  NStore.setisNotice(false)
}
//   //监听一下
// //假设pinia中有一个count变量（const count = ref(0)）
// watch(
//   () => store.getUserId,
//   (newVal, oldVal) => {
//     // TODO
//     console.log("watch")
//   }
// );
let client = ref()
onMounted(() => {
  getRead()

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
    if (json.userId===store.getUserId) {
        NStore.setisNotice(true)
    }
    console.log("NStore.setisNotice(true)")

  }
}

})


</script>

<style scoped lang="scss">
.app-topnav {
  background: #333; //背景颜色

  ul {
    display: flex;
    height: 53px;
    justify-content: flex-end;
    align-items: center;

    li {
      a {
        padding: 0 15px;
        color: #cdcdcd;
        line-height: 1;
        display: inline-block;

        i {
          font-size: 14px;
          margin-right: 2px;
        }

        &:hover {
          color: $xtxColor;
        }
      }

      ~li {
        a {
          border-left: 2px solid #666;
        }
      }
    }
  }
}
</style>