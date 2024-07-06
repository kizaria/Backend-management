<template>
  <div class="xtx-pay-page">
    <div class="container">
      <!-- 支付结果 -->
      <div class="pay-result">
        <span class="iconfont icon-queren2 green"></span>
        <p class="tit">支付成功</p>
        <p class="tip">将尽快发货，收货期间请保持手机畅通</p>
        <p>支付方式：<span>支付宝</span></p>
        <p>支付金额：<span>¥{{route.query.total_amount}}</span></p>
        <div class="btn">
          <el-button type="primary" style="margin-right:20px" @click="$router.push('/myorder')">查看订单</el-button>
          <el-button  @click="$router.push('/')">进入首页</el-button>
        </div>
        <p class="alert">

        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import router from '@/router';
import { UserStore } from '@/stores/user/index';
import { getInfoApi } from '@/api/wxuser/index';
import { collectApi,hasCollectApi } from '@/api/collect/index';
import { getOrderByIdApi } from '@/api/order/index';
import { payApi } from '@/api/alipay/index';

const route = useRoute()

</script>

<style scoped lang="scss">
.pay-result {
  padding: 100px 0;
  background: #fff;
  text-align: center;
  margin-top: 20px;

  >.iconfont {
    font-size: 100px;
  }

  .green {
    color: #1dc779;
  }

  .red {
    color: $priceColor;
  }

  .tit {
    font-size: 24px;
  }

  .tip {
    color: #999;
  }

  p {
    line-height: 40px;
    font-size: 16px;
  }

  .btn {
    margin-top: 50px;
  }

  .alert {
    font-size: 12px;
    color: #999;
    margin-top: 50px;
  }
}
</style>