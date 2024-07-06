<template>
    <el-main>
        <!-- 表格 -->
        <el-table :data="tableList" size="small">
            <el-table-column prop="goodsName" label="商品名称"></el-table-column>

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


            <el-table-column label="操作" align="center" width="220">
                <template #default="scope">

                    <el-button  type="danger" icon="Delete" size="small"
                        @click="cancle(scope.row)">取消收藏</el-button>
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


//获取收藏
let collect=async()=>{
    let res =await getMyCollectApi(searchPram)
    if(res.code=200){
        console.log(res.data.records)
        tableList.value=res.data.records
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
let cancle = async (row: GoodsCollect) => {
    console.log(row)
    let confirm = await global.$myconfirm('确认取消收藏该商品吗？')
    if (confirm) {
        let res = await collectApi({
            userId:user.getUserId,
            goodsId:row.goodsId
        })
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            collect()

        }
    }
}


// 分页方法
//page-size 改变时触发
//pagesize就是当前页显示多少条数据
let sizeChange = (size: number) => {
    searchPram.pageSize = size
    collect()
}

//current-page 改变时触发
let currentChange = (page: number) => {
    searchPram.currentPage = page
    collect()

}






onMounted(() => {
    collect()
})





</script>

<style scoped></style>