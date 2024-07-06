<!-- 商品详情 -->
<template>
  <div class="xtx-goods-page">
    <div class="container">
      <div class="bread-container">
        <el-breadcrumb separator=">">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/commodity' }">闲置商品
          </el-breadcrumb-item>
          <el-breadcrumb-item>{{goods.goodsName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 商品信息 -->
      <div class="info-container">
        <div>
          <div class="goods-info">
            <div class="media">
              <!-- 图片预览区 -->
              <imageView :goodsId="route.params.id"/>
              <!-- 统计数量 -->
              <ul class="goods-sales">
                <li v-if="isHasCollect==1&&store.getUserId!=''">
                  <p>收藏人气</p>
                  <p>{{ noc }}</p>
                  <p @click="collect()">  
                    <el-icon>
                    <CollectionTag />
                    </el-icon>
                  取消收藏</p>
                </li>

                <li v-if="isHasCollect==0&&store.getUserId!=''">
                  <p>收藏人气</p>
                  <p>{{ noc }}</p>
                  <p @click="collect()">  
                    <el-icon>
                    <CollectionTag />
                    </el-icon>
                  收藏商品</p>
                </li>

              </ul>
            </div>
            <div class="spec">
              <!-- 商品信息区 -->
              <p class="g-name"> {{goods.goodsName}} </p>
              <p class="g-desc">{{goods.goodsDesc}} </p>
              <p class="g-price">
                <span>{{goods.goodsPrice}}</span>
                <span>{{goods.goodsPrice}}</span>
              </p>
              <p class="g-desc" style="color: black;">
                <b>卖家：</b>
                {{goods.userName}} <el-icon style="cursor: pointer;" @click="chat(goods.userId)"><ChatDotRound /></el-icon>
              </p>
                
              <p class="g-desc" style="color: black;">
                <b>地址：</b>
                {{goods.address}} 
              </p>

              <p class="g-desc" style="color: black;">
                <b>收货方式：</b>
                <el-radio-group v-model="radio" >
                  <el-radio v-if="goods.type=='0'||goods.type=='1'" :value="1">自提</el-radio>
                  <el-radio v-if="goods.type=='0'||goods.type=='2'" :value="2">快递</el-radio>
                </el-radio-group>
              </p>

              <p class="g-desc" style="color: black;">
                <b>发布日期：</b>
                {{goods.createTime}} 
              </p>
              <div class="g-service">
                <dl>
                  <dt>促销</dt>
                  <dd>临近毕业，超多好物出售</dd>
                </dl>
                <dl>
                  <dt>服务</dt>
                  <dd>
                    <span>无忧退货</span>
                    <span>快速退款</span>
                    <span>免费包邮</span>
                    <a href="javascript:;">了解详情</a>
                  </dd>
                </dl>
              </div>
              <!-- sku组件 -->

              <!-- 数据组件 -->

              <!-- 按钮组件 -->

              <div>
                <el-button size="large" class="btn" @click="report()">
                  举报
                </el-button>

                <el-button size="large" class="btn" @click="checkout()">
                  购买
                </el-button>
              </div>

            </div>
          </div>

        </div>
      </div>
    </div>
  </div>

  <el-dialog v-model="dialog" title="举报商品" width="800">
            <el-form :model="reportModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">


                <el-form-item prop="reason" label="原因:">
                    <el-input type="textarea"  :rows="7" v-model="reportModel.reason"></el-input>
                </el-form-item>

            </el-form>

            <div style="padding-left: 280px; padding-bottom: 30px;">
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div>
        </el-dialog>


</template>

<script setup lang="ts">
import { getGoodsById } from '@/api/goods/index'
import { friendSaveApi } from '@/api/friend/index'
import {getCountApi} from '@/api/collect/index'
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import imageView from '@/components/imageView/index.vue'
import router from '@/router';
import { UserStore } from '@/stores/user/index';
import { getInfoApi } from '@/api/wxuser/index';
import { reportApi } from '@/api/report/index';
import { collectApi,hasCollectApi } from '@/api/collect/index';
import useInstance from '@/hooks/useInstance'
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import useDialog from '@/hooks/useDialog'
import SysDialog from '@/components/SysDialog.vue'
import UploadImage from '@/components/UploadImage.vue'


//获取pinia
let store=UserStore()

//卖家信息
let mjuser=ref()
let mj=ref({})

//获取卖家信息
let getInfo=async()=>{
  let res =await getInfoApi({userId:mjuser.value})
  if(res.code==200){
    console.log("ss")
    console.log(res.data)
    mj.value=res.data
  }
}

const goods = ref({})
const route = useRoute()
const getGoods = async () => {
  const res = await getGoodsById({Id:route.params.id})
  goods.value = res.data
}

//收藏人气
let noc=ref()
let getCount=async()=>{
  let res =await getCountApi({goodsId:route.params.id})
  if(res.data==null){
    noc.value=0;
  }else{
    noc.value=res.data
  }
 
}

//判断是否收藏
let isHasCollect=ref()

let hasCollect=async()=>{
if(store.getUserId==''){
  return;
}
  let res =await hasCollectApi({
    userId:store.getUserId,
    goodsId:goods.value.goodsId
  })
  if(res.code==200){
    isHasCollect.value=res.data
  }
}

//跳转聊天
let chat=async(userId)=>{
  console.log(userId)
  mjuser.value=userId
  getInfo()
  ElMessage('正在跳转中')
  setTimeout(()=>{
		//需要延迟的代码 :3秒后延迟跳转到首页，可以加提示什么的
    ss()

        //延迟时间：3秒
    },1000)
    
}

let ss=async()=>{
  let res=await friendSaveApi({
    userid:store.getUserId,
    username:store.getNickName,
    useravatar:store.getavatar,
    friendid:mj.value.userId,
    friendname:mj.value.username,
    friendavatar:mj.value.picture
  })
  if(res.code==200){
    setTimeout(()=>{
		//需要延迟的代码 :3秒后延迟跳转到首页，可以加提示什么的

    aa()
        //延迟时间：3秒
    },1000)
  }

}

let aa=async()=>{
  let res=await friendSaveApi({
    userid:mj.value.userId,
    username:mj.value.username,
    useravatar:mj.value.picture,
    friendid:store.getUserId,
    friendname:store.getNickName,
    friendavatar:store.getavatar,
  })
  if(res.code==200){

  }
  router.push({path:'/chat'})
}

let radio=ref(0)

let checkout=()=>{
  if(radio.value==0){
    alert("请选择收货方式！")
    return;
  }
  if(goods.value.userId==store.getUserId){
    alert("不能购买自己商品")
    return;
  }
   router.push({path:'/checkout',query: {re:radio.value,goodsId:goods.value.goodsId}})
}

//收藏
let collect=async()=>{
  let res =await collectApi({
    userId:store.getUserId,
    goodsId:goods.value.goodsId
  })
  if(res.code==200){
    //重新获取
    getCount()
    hasCollect()
  }
}

//表单ref属性
let addRef = ref<FormInstance>();


//表单验证规则
let rules = reactive({
  reason: [{ required: true, message: '请输入原因', trigger: ['change', 'blur'] }],
})

//举报表单
let reportModel=reactive({
    goodsId:route.params.id,
    reportUser:store.getUserId,
    reason:''
})

//举报弹框
let dialog=ref(false)

//举报
let report=()=>{
    //清除表单数据
    addRef.value?.resetFields()
    dialog.value = true
}

//新增，编辑提交
let commit = () => {
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await reportApi(reportModel);
            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //关闭弹框
                dialog.value=false
            }

        }
    })
}


//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()

    //关闭弹窗
    dialog.value=false
}



onMounted(()=>{
  getGoods()
  getCount()
  hasCollect()
})
</script>

<style scoped lang='scss'>
.el-radio__input.is-checked + .el-radio__label {
  color: #28d4c1 !important;
}
/* 选中后小圆点的颜色 */
.el-radio__input.is-checked .el-radio__inner {
  background: #28d4c1 !important;
  border-color: #28d4c1 !important;
}

.xtx-goods-page {
  .goods-info {
    min-height: 600px;
    background: #fff;
    display: flex;

    .media {
      width: 580px;
      height: 600px;
      padding: 30px 50px;
    }

    .spec {
      flex: 1;
      padding: 30px 30px 30px 0;
    }
  }

  .goods-footer {
    display: flex;
    margin-top: 20px;

    .goods-article {
      width: 940px;
      margin-right: 20px;
    }

    .goods-aside {
      width: 280px;
      min-height: 1000px;
    }
  }

  .goods-tabs {
    min-height: 600px;
    background: #fff;
  }

  .goods-warn {
    min-height: 600px;
    background: #fff;
    margin-top: 20px;
  }

  .number-box {
    display: flex;
    align-items: center;

    .label {
      width: 60px;
      color: #999;
      padding-left: 10px;
    }
  }

  .g-name {
    font-size: 22px;
  }

  .g-desc {
    color: #999;
    margin-top: 10px;
    .el-radio__input.is-checked + .el-radio__label {
  color: #28d4c1 !important;
}
/* 选中后小圆点的颜色 */
.el-radio__input.is-checked .el-radio__inner {
  background: #28d4c1 !important;
  border-color: #28d4c1 !important;
}

  }

  .g-price {
    margin-top: 10px;

    span {
      &::before {
        content: "¥";
        font-size: 14px;
      }

      &:first-child {
        color: $priceColor;
        margin-right: 10px;
        font-size: 22px;
      }

      &:last-child {
        color: #999;
        text-decoration: line-through;
        font-size: 16px;
      }
    }
  }

  .g-service {
    background: #f5f5f5;
    width: 500px;
    padding: 20px 10px 0 10px;
    margin-top: 10px;

    dl {
      padding-bottom: 20px;
      display: flex;
      align-items: center;

      dt {
        width: 50px;
        color: #999;
      }

      dd {
        color: #666;

        &:last-child {
          span {
            margin-right: 10px;

            &::before {
              content: "•";
              color: $xtxColor;
              margin-right: 2px;
            }
          }

          a {
            color: $xtxColor;
          }
        }
      }
    }
  }

  .goods-sales {
    display: flex;
    width: 400px;
    align-items: center;
    text-align: center;
    height: 140px;

    li {
      flex: 1;
      position: relative;

      ~li::after {
        position: absolute;
        top: 10px;
        left: 0;
        height: 60px;
        border-left: 1px solid #e4e4e4;
        content: "";
      }

      p {
        &:first-child {
          color: #999;
        }

        &:nth-child(2) {
          color: $priceColor;
          margin-top: 10px;
        }

        &:last-child {
          color: #666;
          margin-top: 10px;

          i {
            color: $xtxColor;
            font-size: 14px;
            margin-right: 2px;
          }

          &:hover {
            color: $xtxColor;
            cursor: pointer;
          }
        }
      }
    }
  }
}

.goods-tabs {
  min-height: 600px;
  background: #fff;

  nav {
    height: 70px;
    line-height: 70px;
    display: flex;
    border-bottom: 1px solid #f5f5f5;

    a {
      padding: 0 40px;
      font-size: 18px;
      position: relative;

      >span {
        color: $priceColor;
        font-size: 16px;
        margin-left: 10px;
      }
    }
  }
}

.goods-detail {
  padding: 40px;

  .attrs {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 30px;

    li {
      display: flex;
      margin-bottom: 10px;
      width: 50%;

      .dt {
        width: 100px;
        color: #999;
      }

      .dd {
        flex: 1;
        color: #666;
      }
    }
  }

  >img {
    width: 100%;
  }
}

.btn {
  margin-top: 20px;

}

.bread-container {
  padding: 25px 0;
}
</style>