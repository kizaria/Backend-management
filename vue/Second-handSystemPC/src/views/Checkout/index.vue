<!-- 订单页面 -->
<template>
  <div class="xtx-pay-checkout-page">
    <div class="container">
      <div class="wrapper">
        <!-- 收货地址 -->
        <div v-if="Jr == 2">
          <h3 class="box-title">收货地址</h3>
          <div class="box-body">
            <div class="address">
              <div class="text">
                <div class="none" v-if="!curAddress">您需要先添加收货地址才可提交订单。</div>
                <ul v-else>
                  <li><span>收<i />货<i />人：</span>{{ curAddress.nickname }}</li>
                  <li><span>联系方式：</span>{{ curAddress.phone }}</li>
                  <li><span>收货地址：</span>{{ curAddress.addressdesc }}</li>
                </ul>
              </div>
              <div class="action">
                <el-button size="large" @click="showDialog = true">切换地址</el-button>
                <el-button size="large" @click="showSaveDialog = true">添加地址</el-button>
              </div>
            </div>
          </div>
        </div>

        <div v-else>
          <h3 class="box-title">因选择自提无需选择快递地址</h3>
        </div>

        <!-- 商品信息 -->
        <h3 class="box-title">商品信息</h3>
        <div class="box-body">
          <table class="goods">
            <thead>
              <tr>
                <th width="520">商品信息</th>
                <th width="170">单价</th>
                <th width="170">数量</th>
                <th width="170">小计</th>
                <th width="170">实付</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                  <a href="javascript:;" class="info">
                    <img :src="goods?.image" alt="">
                    <div class="right">
                      <p>{{ goods?.goodsName }}</p>
                      <p>{{ goods?.goodsDesc }}</p>
                    </div>
                  </a>
                </td>
                <td>&yen;{{ goods?.goodsPrice }}</td>
                <td>1</td>
                <td>&yen;{{ goods?.goodsPrice }}</td>
                <td>&yen;{{ goods?.goodsPrice }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- 金额明细 -->
        <h3 class="box-title">金额明细</h3>
        <div class="box-body">
          <div class="total">
            <dl>
              <dt>商品件数：</dt>
              <dd>1件</dd>
            </dl>
            <dl>
              <dt>商品总价：</dt>
              <dd>{{ goods?.goodsPrice }}</dd>
            </dl>
            <dl>
              <dt>应付总额：</dt>
              <dd class="price">{{ goods?.goodsPrice }}</dd>
            </dl>
          </div>
        </div>
        <!-- 提交订单 -->
        <div class="submit">
          <el-button type="primary" size="large" @click="submit()">提交订单</el-button>
        </div>
      </div>
    </div>
  </div>
  <!-- 切换地址 -->
  <el-dialog  v-model="showDialog" title="切换收货地址" width="30%" center>
    <div class="addressWrapper">
      <div class="text item" v-for="item in address" :key="item.addressid"
        :class="{ active: activeAddress.addressid === item.addressid }" @click="switchAddress(item)">
        <ul>
          <li><span>收<i />货<i />人：</span>{{ item.nickname }}</li>
          <li><span>联系方式：</span>{{ item.phone }}</li>
          <li><span>收货地址：</span>{{ item.addressdesc }}</li>
        </ul>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel()">取消</el-button>
        <el-button type="primary" @click="confirm()">确定</el-button>
      </span>
    </template>
  </el-dialog>
  <!-- 添加地址 -->
  <el-dialog v-model="showSaveDialog" title="切换收货地址" width="30%" center>
    <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">


      <el-form-item prop="nickname" label="联系人:">
        <el-input v-model="addModel.nickname"></el-input>
      </el-form-item>

      <el-form-item label-width="auto" prop="phone" label="联系电话:">
        <el-input v-model="addModel.phone"></el-input>
      </el-form-item>

      <el-form-item prop="addressdesc" label="详细地址:">
        <el-input :rows="4" type="textarea" v-model="addModel.addressdesc"></el-input>
      </el-form-item>


    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
      </span>
    </template>

    <!-- <div style="padding-left: 350px; padding-bottom: 30px;">
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div> -->
  </el-dialog>
</template>

<script setup lang="ts">
import { onMounted, ref,reactive } from 'vue'
import router from '@/router';
import { useRoute } from 'vue-router'
import { UserStore } from '@/stores/user/index';
import { getAddressApi ,saveAddressApi} from '@/api/address/index'
import { getGoodsById } from '@/api/goods/index'
import { replaceOrderApi } from '@/api/order/index'
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'


const route = useRoute()

const checkInfo = {}  // 订单对象
// const curAddress = {}  // 地址对象
//判断是否需要地址信息
let Jr = ref()
let receiving = () => {
  Jr.value = route.query.re
}
let showDialog = ref(false)
let showSaveDialog = ref(false)
//获取pinia
let store = UserStore()
let address = ref([])
let curAddress = ref()
//获取地址信息
let getAddress = async () => {
  let res = await getAddressApi({ userId: store.getUserId })
  if (res.code == 200) {
    address.value = res.data
    let item = address.value.find(item => item.isdefault == 1)
    curAddress.value = item
    //检查一下
    // console.log(address.value)
    // console.log(curAddress.value)
  }
}

//取得商品id和商品详情
let goods = ref()
let getGoods = async () => {
  let goodsId = route.query.goodsId
  let res = await getGoodsById({ Id: goodsId })
  if (res.code == 200) {
    goods.value = res.data
  }
}

// 切换地址
const activeAddress = ref({})
const switchAddress = (item) => {
  activeAddress.value = item
}
//新增表单数据
let addModel = reactive({
  userid: store.getUserId,
  nickname: '',
  phone: '',
  addressdesc: '',
})
//表单ref属性
let addRef = ref<FormInstance>();

let confirm = () => {
  curAddress.value = activeAddress.value
  showDialog.value = false
  activeAddress.value = {}
}


//表单验证规则
let rules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: ['change', 'blur'] }],
  phone: [{ required: true, message: '请输入联系电话', trigger: ['change', 'blur'] }],
  addressdesc: [{ required: true, message: '请输入地址', trigger: ['change', 'blur'] }],
}
//重置
let resetBtn = () => {
  //清除表单数据
  addRef.value?.resetFields()
  showSaveDialog.value=false
}

//提交
let commit = async () => {
  //表单验证
  addRef.value?.validate(async (valid) => {
    if (valid) {
      //提交数据
      let res = await saveAddressApi(addModel)
      //如果res返回，并且code等于200
      if (res && res.code == 200) {
        //信息提示
        ElMessage.success(res.msg)
        addRef.value?.resetFields()
        showSaveDialog.value=false
        getAddress()
      }

    }
  })
}
let cancel = () => {
  showDialog.value = false
}

// 
let client = ref()
//订单
let submit = async () => {
  if(curAddress.value==null&&Jr.value=='2'){
    alert("请选择地址，在下单")
    return;
  }
  if(Jr.value=='1'){
    let res = await replaceOrderApi({
    goodsId: goods.value.goodsId,
    orderUser: store.getUserId,
    price: goods.value.goodsPrice,
    goodsName: goods.value.goodsName,
    addressid:''
  })
  if (res.code == 200) {
    console.log(res.data)
    let orderId = res.data.orderId
    console.log(orderId)
    router.push({ path: '/pay', query: { id: orderId } })



    if (client) {
      client.send(JSON.stringify(getMessaget()))
    }

  }
  }else{
    let res = await replaceOrderApi({
    goodsId: goods.value.goodsId,
    orderUser: store.getUserId,
    price: goods.value.goodsPrice,
    goodsName: goods.value.goodsName,
    addressid:curAddress.value.addressid
  })
  if (res.code == 200) {
    console.log(res.data)
    let orderId = res.data.orderId
    console.log(orderId)
    router.push({ path: '/pay', query: { id: orderId } })



    if (client) {
      client.send(JSON.stringify(getMessaget()))
    }

  }
  }

}


let getMessaget = () => {
  return {
    userId: goods.value.userId,
    content: "你的【" + goods.value.goodsName + "】被购买",
  }
}
onMounted(() => {
  receiving()
  getAddress()
  getGoods()



  client = new WebSocket('ws://localhost:8090/imserverOrder/')

  client.onopen = () => {
    console.log('web open')
  }

  client.onclose = () => {
    console.log('web close')
  }



})

</script>

<style scoped lang="scss">
.xtx-pay-checkout-page {
  margin-top: 20px;

  .wrapper {
    background: #fff;
    padding: 0 20px;

    .box-title {
      font-size: 16px;
      font-weight: normal;
      padding-left: 10px;
      line-height: 70px;
      border-bottom: 1px solid #f5f5f5;
    }

    .box-body {
      padding: 20px 0;
    }
  }
}

.address {
  border: 1px solid #f5f5f5;
  display: flex;
  align-items: center;

  .text {
    flex: 1;
    min-height: 90px;
    display: flex;
    align-items: center;

    .none {
      line-height: 90px;
      color: #999;
      text-align: center;
      width: 100%;
    }

    >ul {
      flex: 1;
      padding: 20px;

      li {
        line-height: 30px;

        span {
          color: #999;
          margin-right: 5px;

          >i {
            width: 0.5em;
            display: inline-block;
          }
        }
      }
    }

    >a {
      color: $xtxColor;
      width: 160px;
      text-align: center;
      height: 90px;
      line-height: 90px;
      border-right: 1px solid #f5f5f5;
    }
  }

  .action {
    width: 420px;
    text-align: center;

    .btn {
      width: 140px;
      height: 46px;
      line-height: 44px;
      font-size: 14px;

      &:first-child {
        margin-right: 10px;
      }
    }
  }
}

.goods {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;

  .info {
    display: flex;
    text-align: left;

    img {
      width: 70px;
      height: 70px;
      margin-right: 20px;
    }

    .right {
      line-height: 24px;

      p {
        &:last-child {
          color: #999;
        }
      }
    }
  }

  tr {
    th {
      background: #f5f5f5;
      font-weight: normal;
    }

    td,
    th {
      text-align: center;
      padding: 20px;
      border-bottom: 1px solid #f5f5f5;

      &:first-child {
        border-left: 1px solid #f5f5f5;
      }

      &:last-child {
        border-right: 1px solid #f5f5f5;
      }
    }
  }
}

.my-btn {
  width: 228px;
  height: 50px;
  border: 1px solid #e4e4e4;
  text-align: center;
  line-height: 48px;
  margin-right: 25px;
  color: #666666;
  display: inline-block;

  &.active,
  &:hover {
    border-color: $xtxColor;
  }
}

.total {
  dl {
    display: flex;
    justify-content: flex-end;
    line-height: 50px;

    dt {
      i {
        display: inline-block;
        width: 2em;
      }
    }

    dd {
      width: 240px;
      text-align: right;
      padding-right: 70px;

      &.price {
        font-size: 20px;
        color: $priceColor;
      }
    }
  }
}

.submit {
  text-align: right;
  padding: 60px;
  border-top: 1px solid #f5f5f5;
}

.addressWrapper {
  max-height: 500px;
  overflow-y: auto;
}

.text {
  flex: 1;
  min-height: 90px;
  display: flex;
  align-items: center;

  &.item {
    border: 1px solid #f5f5f5;
    margin-bottom: 10px;
    cursor: pointer;

    &.active,
    &:hover {
      border-color: $xtxColor;
      background: lighten($xtxColor, 50%);
    }

    >ul {
      padding: 10px;
      font-size: 14px;
      line-height: 30px;
    }
  }
}
</style>