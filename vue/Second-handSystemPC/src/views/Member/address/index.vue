<template>
    <el-main>
        <!-- 表格 -->
        <el-table :data="tableList" size="small">
            <el-table-column prop="nickname" label="联系人"></el-table-column>


            <el-table-column prop="phone" label="联系电话"></el-table-column>
            <el-table-column prop="addressdesc" label="详细地址"></el-table-column>


            <el-table-column prop="isdefault" label="上下架">
                <template #default="scope">
                    <el-button v-if="scope.row.isdefault == '0'" size="small" type="danger" effect="dark"
                        @click="editstatus(scope.row)">设为默认地址</el-button>
                    <el-button v-if="scope.row.isdefault == '1'" size="small" type="primary"
                        @click="editstatus(scope.row)" effect="dark">默认地址</el-button>
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="220">
                <template #default="scope">
                    <el-button type="primary" icon="Edit" size="small" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button  type="danger" icon="Delete" size="small"
                        @click="deleteBtn(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="sizeChange" @current-change="currentChange"
            :current-page.sync="searchPram.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchPram.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchPram.total"
            background>
        </el-pagination>








        <el-dialog v-model="dialogTableVisible" title="编辑地址" width="800">
            <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">


                <el-form-item prop="nickname" label="联系人:">
                    <el-input v-model="addModel.nickname"></el-input>
                </el-form-item>

                <el-form-item label-width="auto" prop="phone" label="联系电话:">
                    <el-input v-model="addModel.phone"></el-input>
                </el-form-item>

                <el-form-item label-width="auto" prop="addressdesc" label="详细地址:">
                    <el-input :rows="4" type="textarea" v-model="addModel.addressdesc"></el-input>
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
import { deleteApi, getGoodsListApi, setIndexApi, upanddownApi, editGoodsApi, categoryApi, getMyUnusedListApi } from '@/api/goods/index'
import { type Goods, type GoodsListParm, type UpdateParm } from '@/api/goods/GoodsModel'
import useInstance from '@/hooks/useInstance'
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import useDialog from '@/hooks/useDialog'
import SysDialog from '@/components/SysDialog.vue'
import UploadImage from '@/components/UploadImage.vue'
import type { NewType } from '@/type/BaseType'
import { UserStore } from '@/stores/user'
import { getAddressApi, editAddressApi, defaultAddressApi, deleteAddressApi, getListApi } from '@/api/address/index'

//弹窗关闭属性
let dialogTableVisible = ref(false)

//获取token
let user = UserStore()
//获取全局属性
let { global } = useInstance()
//表单ref属性
let addRef = ref<FormInstance>();
//获取弹框属性
let { dialog, onClose, onConfirm, onShow } = useDialog();


//搜索绑定的对象
let searchPram = reactive({
    currentPage: 1,
    pageSize: 10,
    userId: user.getUserId,
    total: 0,
})

//新增表单数据
let addModel = reactive({
    nickname: '',
    phone: '',
    addressdesc: '',
})

//表单验证规则
let rules = reactive({
    nickname: [{ required: true, message: '请输入昵称', trigger: ['change', 'blur'] }],
    phone: [{ required: true, message: '请输入联系电话', trigger: ['change', 'blur'] }],
    addressdesc: [{ required: true, message: '请输入地址', trigger: ['change', 'blur'] }],
})

//新增，编辑提交
let commit = () => {
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await editAddressApi(addModel);

            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //刷新表格
                getGoodsList()
                //关闭弹框
                dialogTableVisible.value = false
            }

        }
    })
}


//编辑
let editBtn = (row: any) => {
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


//搜索
let searchBtn = () => {
    getGoodsList()
}

//在输入栏回车搜索
let searchKey = () => {
    getGoodsList()
}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()
    //关闭弹窗
    dialogTableVisible.value = false
}

//表格数据
let tableList = ref([])


//列表查询
let getGoodsList = async () => {
    let res = await getListApi(searchPram)
    console.log(res)
    if (res && res.code == 200) {
        tableList.value = res.data.records;
        searchPram.total = res.data.total;
    }
}



//删除
let deleteBtn = async (row: any) => {
    let confirm = await global.$myconfirm('确认删除该商品吗？')

    if (confirm) {
        let res = await deleteAddressApi({ addressid: row.addressid })
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            getGoodsList()
        }
    }
}

//修改状态
let editstatus = async (row: any) => {
    let confirm = await global.$myconfirm('确认上下架该商品吗？')
    console.log(global)
    if (confirm) {
        let res = await defaultAddressApi(row)
        //如果res返回，并且code等于200
        if (res && res.code == 200) {
            //信息提示
            ElMessage.success(res.msg)
            //刷新表格
            getGoodsList()
        }
    }
}


// 分页方法
//page-size 改变时触发
//pagesize就是当前页显示多少条数据
let sizeChange = (size: number) => {
    searchPram.pageSize = size
    getGoodsList()
}

//current-page 改变时触发
let currentChange = (page: number) => {
    searchPram.currentPage = page
    getGoodsList()

}






onMounted(() => {

    getGoodsList()
})





</script>

<style scoped></style>