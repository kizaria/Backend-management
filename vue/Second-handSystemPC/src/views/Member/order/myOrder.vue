<template>
    <el-main>
        <!-- 表格 -->
        <el-table :data="tableList" size="small">
            <el-table-column prop="goodsName" label="商品名称" ></el-table-column>

            <el-table-column prop="image" label="图片">
                <template #default="scope">
                    <el-image v-if="scope.row.image" style="width: 50px; height: 50px"
                        :src="scope.row.image.split(',')[0]" />
                </template>
            </el-table-column>

            <el-table-column label="商品详情">
                <template #default="scope" >
                    <el-button size="small" type="primary" effect="dark" @click="$router.push(`/detail/${scope.row.goodsId}`)">打开商品链接</el-button>   
                </template>
            </el-table-column>


            <el-table-column prop="orderPay" label="付款状态"  width="100">
                <template #default="scope">
                    <p v-if="scope.row.orderPay=='0'" >未付款</p>
                    <p v-if="scope.row.orderPay=='1'" >已付款</p>
                </template>
            </el-table-column>

            <el-table-column prop="orderStatus" label="收货状态" width="100">
                <template #default="scope">
                    <p v-if="scope.row.orderStatus=='0'" >未收货</p>
                    <p v-if="scope.row.orderStatus=='1'" >已收货</p>
                </template>
            </el-table-column>

            


            <el-table-column prop="orderReceiving" label="发货状态" width="100">
                <template #default="scope">
                    <p v-if="scope.row.orderReceiving=='0'" >未发货</p>
                    <p v-if="scope.row.orderReceiving=='1'" >已发货</p>
                </template>
            </el-table-column>

            <el-table-column prop="orderExpress" label="快递号" width="100">
            </el-table-column>

            <el-table-column label="操作" align="center" width="350">
                <template #default="scope">
                    <el-button v-if="scope.row.orderPay=='0'"    size="small"
                        @click="pay(scope.row)">去支付</el-button>
                        
                        <el-button v-if="scope.row.orderStatus=='0'&&scope.row.orderPay=='1'&&scope.row.orderReceiving=='1'" size="small"
                        @click="receiving(scope.row)">确认收货</el-button>

                    <el-button   icon="Delete" size="small"
                        @click="cancle(scope.row)">删除订单</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="sizeChange" @current-change="currentChange"
            :current-page.sync="searchPram.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchPram.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchPram.total"
            background>
        </el-pagination>

    </el-main>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, nextTick } from 'vue'
import { deleteApi, getGoodsListApi, setIndexApi, upanddownApi, editGoodsApi, categoryApi } from '@/api/goods/index'
import { type Goods, type GoodsListParm, type UpdateParm } from '@/api/goods/GoodsModel'
import useInstance from '@/hooks/useInstance'
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import useDialog from '@/hooks/useDialog'
import SysDialog from '@/components/SysDialog.vue'
import UploadImage from '@/components/UploadImage.vue'
import type { NewType } from '@/type/BaseType'
import { UserStore } from '@/stores/user'
import { getMyCollectApi,collectApi } from '@/api/collect/index'
import type { GoodsCollect } from '@/api/collect/collectModel'
import { confirmApi, deleteOrderApi, getMyOrderApi, getSellOrderApi } from '@/api/order/index'

//获取订单
let order=async()=>{
    let res =await getMyOrderApi(searchPram)
    if(res.code=200){
        console.log(res.data.records)
        tableList.value=res.data.records
        searchPram.total = res.data.total;
    }
}

//获取token
let user = UserStore()
//获取全局属性
let { global } = useInstance()


//搜索绑定的对象
let searchPram = reactive({
    currentPage: 1,
    pageSize: 10,
    userId:user.getUserId,
    total: 0,
})



//表格数据
let tableList = ref([])


//取消收藏
let cancle = async (row: any) => {
    console.log(row)
    let confirm = await global.$myconfirm('确认删除该订单吗？')
    if (confirm) {
        let res = await deleteOrderApi({
            orderId: row.orderId,
        })
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            order()

        }
    }
}

//去支付
let pay=(row:any)=>{
    window.open(`http://localhost:8090/api/alipay/pay?subject=${row.goodsName}&traceNo=${row.orderId}&totalAmount=${row.price}`)
}

//确认收货
let receiving=async (row:any)=>{
    let res=await confirmApi({
        orderId:row.orderId
    })
    if(res.code==200){
        ElMessage.success(res.msg)
        order()
    }
}


// 分页方法
//page-size 改变时触发
//pagesize就是当前页显示多少条数据
let sizeChange = (size: number) => {
    searchPram.pageSize = size
    order()
}

//current-page 改变时触发
let currentChange = (page: number) => {
    searchPram.currentPage = page
    order()

}



onMounted(() => {
    order()
})





</script>

<style scoped></style>