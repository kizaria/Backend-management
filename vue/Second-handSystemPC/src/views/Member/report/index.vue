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

            
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <p v-if="scope.row.reportStatus=='0'" >未处理</p>
                    <p v-if="scope.row.reportStatus=='1'" >已处理</p>
                    <p v-if="scope.row.reportStatus=='2'" >已驳回</p>
                </template>
            </el-table-column>



            <el-table-column label="操作" align="center" width="220">
                <template #default="scope">
                    <el-button   icon="edit" size="small"
                        @click="editBtn(scope.row)">修改信息</el-button>

                    <el-button  type="danger" icon="Delete" size="small"
                        @click="cancle(scope.row)">取消举报</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="sizeChange" @current-change="currentChange"
            :current-page.sync="searchPram.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchPram.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchPram.total"
            background>
        </el-pagination>

        <el-dialog v-model="dialogTableVisible" title="举报商品" width="800">
            <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">

                <el-form-item label-width="auto" prop="reason" label="举报原因:">
                    <el-input :rows="4" type="textarea"  v-model="addModel.reason"></el-input>
                </el-form-item>


            </el-form>

            <div style="padding-left: 280px; padding-bottom: 30px;">
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div>
        </el-dialog>

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
import { getMyReportApi,cancelReportApi, editReportApi } from '@/api/report/index'
import type { ReportParm } from '@/api/report/ReportModel'

//获取举报
let report=async()=>{
    let res =await getMyReportApi(searchPram)
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


//取消举报
let cancle = async (row: any) => {
    console.log(row)
    let confirm = await global.$myconfirm('确认取消举报该商品吗？')
    if (confirm) {
        let res = await cancelReportApi({
            userId:user.getUserId,
            goodsId:row.goodsId
        })
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            report()

        }
    }
}


//弹窗关闭属性
let dialogTableVisible = ref(false)

//表单ref属性
let addRef = ref<FormInstance>();

//新增表单数据
let addModel = reactive({
    goodsId:'',
    reason:'',
    reportUser: user.getUserId,
})

//表单验证规则
let rules = reactive({
    reason: [{ required: true, message: '请输入标题', trigger: ['change', 'blur'] }],
})

//新增，编辑提交
let commit = () => {
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await editReportApi(addModel);
            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //刷新表格
                report()
                //关闭弹框
                dialogTableVisible.value=false
            }

        }
    })
}


//编辑
let editBtn = (row: ReportParm) => {
    console.log(row)
    //清除表单数据
    addRef.value?.resetFields()
    dialogTableVisible.value = true
    //设置数据回显
    //nextTick在状态改变后立即使用，简称立刻更新
    nextTick(() => {
        Object.assign(addModel, row);
    })

}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()

    //关闭弹窗
    dialogTableVisible.value=false
}



// 分页方法
//page-size 改变时触发
//pagesize就是当前页显示多少条数据
let sizeChange = (size: number) => {
    searchPram.pageSize = size
    report()
}

//current-page 改变时触发
let currentChange = (page: number) => {
    searchPram.currentPage = page
    report()

}



onMounted(() => {
    report()
})





</script>

<style scoped></style>